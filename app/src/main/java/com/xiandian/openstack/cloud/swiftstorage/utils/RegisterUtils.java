package com.xiandian.openstack.cloud.swiftstorage.utils;

import net.sf.json.JSONObject;

import java.io.IOException;



import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 注册工具类
 * 
 */
public class RegisterUtils {

	public boolean bindRole(String tokenId, String url) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		Request request = new Request.Builder()
				.addHeader("Content-Type", "application/json")
				.addHeader("Content-Length", "69")
				.addHeader("X-Auth-Token", tokenId).url(url)
				.put(RequestBody.create(mediaType, "")).build();
		Response response;
		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException(response.toString());
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *
	 * @param email 邮箱
	 * @param password 密码
	 * @param name 用户名称
	 * @param tenantId 租户ID
	 * @param tokenId 令牌ID
     * @param url 地址
     * @return
     */
	public String createUser(String email, String password, String name,
			String tenantId, String tokenId, String url) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		JSONObject json = new JSONObject();
		json.put("email", email);
		json.put("password", password);
		json.put("enabled", true);
		json.put("name", name);
		json.put("tenantId", tenantId);
		JSONObject json1 = new JSONObject();
		json1.put("user", json);
		String postBody = json1.toString();

		Request request = new Request.Builder()
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Auth-Token", tokenId).url(url)
				.post(RequestBody.create(mediaType, postBody)).build();

		Response response;
		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException(response.toString());
			}
			String result = response.body().string();
			JSONObject jsonobj = JSONObject.fromObject(result);
			JSONObject jsonobj1 = JSONObject.fromObject(jsonobj.get("user"));
			String userId = jsonobj1.getString("id");
			System.out.println("userId:" + userId);
			return userId;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String createTenant(String username, String tokenId, String url) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		JSONObject json = new JSONObject();
		json.put("name", username);
		json.put("description", "A Description of" + username);
		json.put("enabled", true);
		JSONObject json1 = new JSONObject();
		json1.put("tenant", json);
		String postBody = json1.toString();

		Request request = new Request.Builder()
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.addHeader("X-Auth-Token", tokenId).url(url)
				.post(RequestBody.create(mediaType, postBody)).build();

		Response response;
		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException(response.toString());
			}
			String result = response.body().string();
			JSONObject jsonobj = JSONObject.fromObject(result);
			JSONObject jsonobj1 = JSONObject.fromObject(jsonobj.get("tenant"));
			String tenantId = jsonobj1.get("id").toString();
			System.out.println("tenantId:" + tenantId);
			return tenantId;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public  String getToken(String username, String password, String tenantName,
			String url) {

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");

		JSONObject json = new JSONObject();
		json.put("username", username);
		json.put("password", password);
		JSONObject json1 = new JSONObject();
		json1.put("passwordCredentials", json);
		json1.put("tenantName", tenantName);
		JSONObject json2 = new JSONObject();
		json2.put("auth", json1);
		String postBody = json2.toString();

		Request request = new Request.Builder()
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json").url(url)
				.post(RequestBody.create(mediaType, postBody)).build();
		Response response;
		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException(response.toString());
			}
			String result = response.body().string();
			JSONObject jsonobj = JSONObject.fromObject(result);
			JSONObject jsonobj1 = JSONObject.fromObject(jsonobj.get("access"));
			JSONObject jsonobj2 = JSONObject.fromObject(jsonobj1.get("token"));
			// JSONObject jsonobj3 = new JSONObject(jsonobj2.get("id"));
			String tokenId = jsonobj2.get("id").toString();
			System.out.println("TOKENID:" + tokenId);
			return tokenId;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
