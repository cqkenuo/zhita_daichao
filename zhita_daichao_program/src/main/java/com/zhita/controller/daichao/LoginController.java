package com.zhita.controller.daichao;


import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.service.login.IntLoginService;



@Controller
@RequestMapping(value="/admin_login")
public class LoginController {
	@Resource(name="loginServiceImp")
	private IntLoginService intLoginService;

	public IntLoginService getIntLoginService() {
		return intLoginService;
	}

	public void setIntLoginService(IntLoginService intLoginService) {
		this.intLoginService = intLoginService;
	}

	@RequestMapping("/login")
	@ResponseBody
		public Object getPhoneNumber(String encryptedData, String iv, String session_key) {
			// 被加密的数据
			byte[] dataByte = Base64.decode(encryptedData);
			// 加密秘钥
			byte[] keyByte = Base64.decode(session_key);
			// 偏移量
			byte[] ivByte = Base64.decode(iv);
			try {
				// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
				int base = 16;
				if (keyByte.length % base != 0) {
					int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
					byte[] temp = new byte[groups * base];
					Arrays.fill(temp, (byte) 0);
					System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
					keyByte = temp;
				}
				// 初始化
				Security.addProvider(new BouncyCastleProvider());
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
				AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
				parameters.init(new IvParameterSpec(ivByte));
				cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
				byte[] resultByte = cipher.doFinal(dataByte);
				if (null != resultByte && resultByte.length > 0) {
					String result = new String(resultByte, "UTF-8");
					return JSONObject.parseObject(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

	}

