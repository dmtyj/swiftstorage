package com.xiandian.openstack.cloud.swiftstorage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiandian.openstack.cloud.swiftstorage.base.TaskResult;
import com.xiandian.openstack.cloud.swiftstorage.utils.RegisterUtils;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText txtusername = (EditText) findViewById(R.id.txtUsername);
        final EditText txtpassword = (EditText) findViewById(R.id.txtPassword);
        final EditText txtemail = (EditText) findViewById(R.id.txtemail);
        Button button = (Button) findViewById(R.id.btnregister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=txtusername.getText().toString();
                String password=txtpassword.getText().toString();
                String email=txtemail.getText().toString();
                RegisterTask registerTask = new RegisterTask(userName,password,email);
                registerTask.execute();
            }
        });
    }
    public class RegisterTask extends AsyncTask<String, Object, TaskResult<String>> {
        String userName;
        String password;
        String email;
        public RegisterTask(String userName,String password,String email){
            this.userName=userName;
            this.password=password;
            this.email=email;
        }
        /**
         * 异步UI调用。
         */
        @Override
        protected TaskResult<String> doInBackground(String... params) {

            String adminUsername = "admin";
            String adminPassword = "XiandianSwift";
            String adminTenantName = "admin";
            String getTokenUrl = "http://58.214.31.6:35357/v2.0/tokens";
            String createTenantUrl = "http://58.214.31.6:35357/v2.0/tenants";
            String createUserUrl = "http://58.214.31.6:35357/v2.0/users";
            String swiftRoleId = "faf8812d25e34de9b82421d9d3148162";

            RegisterUtils registerUtils=new RegisterUtils();
            String tokenId = registerUtils.getToken(adminUsername, adminPassword,
                    adminTenantName, getTokenUrl);
            String tenantId = registerUtils.createTenant(userName, tokenId, createTenantUrl);
            String userId = registerUtils.createUser(email, password, userName, tenantId,
                    tokenId, createUserUrl);
            String bindRoleUrl = "http://58.214.31.6:35357/v2.0/tenants/"
                    + tenantId + "/users/" + userId + "/roles/OS-KSADM/"
                    + swiftRoleId;
            boolean flag = registerUtils.bindRole(tokenId, bindRoleUrl);
            Toast.makeText(RegisterActivity.this,"注册成功请登录",Toast.LENGTH_LONG).show();
            return new TaskResult<String>(userName);
        }

        @Override
        protected void onPostExecute(TaskResult<String> stringTaskResult) {
            super.onPostExecute(stringTaskResult);
            if(stringTaskResult.isValid()){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


}
