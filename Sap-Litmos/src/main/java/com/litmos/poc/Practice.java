package com.litmos.poc;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
public class Practice {
	public static void main(String[] args) {
		JSONObject json = new JSONObject(); 
		@SuppressWarnings("unused")
		StringBuffer url = new StringBuffer("http://json-schema.org/draft-04/schema#");
		try {	
			json.put("$schema","http://json-schema.org/draft-04/schema#" )	;	

			json.put("title", "Followers");
			json.put("type", "array");

			JSONObject typestringjson = new JSONObject();
			typestringjson.put("type", "String");

			JSONObject itemsjson = new JSONObject();
			itemsjson.put("title", "Follower");
			itemsjson.put("type", "object");

			JSONObject propertiesjson =new JSONObject();

			propertiesjson.put("login", typestringjson);

			JSONObject idjson = new JSONObject();
			idjson.put("type", "number");
			propertiesjson.put("id", idjson);

			propertiesjson.put("avatar_url", typestringjson);

			propertiesjson.put("gravatar_url", typestringjson);

			propertiesjson.put("url", typestringjson);

			propertiesjson.put("html_url", typestringjson);

			propertiesjson.put("followers_url", typestringjson);

			propertiesjson.put("following_url", typestringjson);

			propertiesjson.put("gists_url", typestringjson);

			propertiesjson.put("starred_url", typestringjson);

			propertiesjson.put("subscriptions_url", typestringjson);

			propertiesjson.put("organizations_url", typestringjson);

			propertiesjson.put("repos_url", typestringjson);

			propertiesjson.put("received_events_url", typestringjson);

			propertiesjson.put("type", typestringjson);

			propertiesjson.put("site_admin", typestringjson);

			itemsjson.put("properties", propertiesjson);
			json.put("items",itemsjson);

			json.put("items",itemsjson);
		}
		catch(JSONException e) {

			e.printStackTrace();
		}

		String jsonstr = json.toString().replace("\\", "");
		String jsonstr1= jsonstr.replace("\"", "\\"+"\"");
		String jsonstr2 = "\""+jsonstr1+"\"";
		String jsonstr3 = jsonstr2.replace("{", "{\\n");
		System.out.print(jsonstr3);

	}
}