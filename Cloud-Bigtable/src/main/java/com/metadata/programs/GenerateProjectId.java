package com.metadata.programs;

public class GenerateProjectId {
	public static void main(String[] args) {
        String serviceAccountID = "bigtabledemo@info2019.iam.gserviceaccount.com";
        String projectId = serviceAccountID.substring(serviceAccountID.indexOf("@")+1 ,serviceAccountID.indexOf("."));
        System.out.println(projectId);
	}
}
