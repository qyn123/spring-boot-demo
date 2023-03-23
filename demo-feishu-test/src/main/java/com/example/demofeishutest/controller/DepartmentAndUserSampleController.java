package com.example.demofeishutest.controller;

import com.lark.oapi.Client;
import com.lark.oapi.core.request.RequestOptions;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.contact.v3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author qiaoyanan
 * date:2023/03/22 15:09
 * 获取单位信息-》获取单位绑定的部门列表-》获取部门下的员工信息
 */
@RestController
public class DepartmentAndUserSampleController {

    @Value("${feishu.app.id}")
    private String appId;

    @Value("${feishu.app.secret}")
    private String appSecret;

    @Value("${feishu.app.user.access.token}")
    private String userAccessToken;

    @Value("${feishu.app.tenant.access.token}")
    private String tenantAccessToken;


    /**
     * 获取单位绑定的部门列表
     */
    @GetMapping("/listDepartmentUnitSample/{companyId}")
    public Object listDepartmentUnitSample(@PathVariable(value = "companyId") String companyId) {

        // 构建client
        Client client = Client.newBuilder(appId, appSecret)
                .disableTokenCache() //如需SDK自动管理租户Token的获取与刷新,可删除该行
                .build();

        // 创建请求对象
        ListDepartmentUnitReq req = ListDepartmentUnitReq.newBuilder()
                .unitId(companyId)
                .departmentIdType("open_department_id")
                .pageSize(50)
                .build();

        // 发起请求
        // 如开启了Sdk的token管理功能，就无需调用 RequestOptions.newBuilder().tenantAccessToken("t-xxx").build()来设置租户token了
        ListDepartmentUnitResp resp = null;
        try {
            resp = client.contact().unit().listDepartment(req, RequestOptions.newBuilder()
                    .tenantAccessToken(tenantAccessToken)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return "error";
        }

        // 业务数据处理
        return Jsons.DEFAULT.toJson(resp.getData());

    }

    /**
     * 获取部门信息列表(历史版本-不推荐)
     */
    @GetMapping("/listDepartmentSample")
    public Object listDepartmentSample() {
        // 构建client
        Client client = Client.newBuilder(appId, appSecret).build();

        // 创建请求对象
        ListDepartmentReq req = ListDepartmentReq.newBuilder()
                .userIdType("open_id")
                .departmentIdType("open_department_id")
                .pageSize(20)
                .build();

        // 发起请求
        ListDepartmentResp resp = null;
        try {
            resp = client.contact().department().list(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return "error";
        }

        // 业务数据处理
        return Jsons.DEFAULT.toJson(resp.getData());
    }

    /**
     * 获取子部门列表
     *
     * @param departmentId 部门ID 必填
     */
    @GetMapping("/childrenDepartmentSample/{departmentId}")
    public Object childrenDepartmentSample(@PathVariable(value = "departmentId") String departmentId) {
        List<Department[]> list = new ArrayList<>();

        int pageSize = 10;
        String pageToken = null;
        boolean hasMore = true;
        ChildrenDepartmentResp childrenDepartmentResp = getChildrenDepartment(departmentId, pageToken, pageSize);
        if (!childrenDepartmentResp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", childrenDepartmentResp.getCode(), childrenDepartmentResp.getMsg(), childrenDepartmentResp.getRequestId()));
            return "error";
        } else {
            ChildrenDepartmentRespBody respData = childrenDepartmentResp.getData();
            // 如果has_more为true，说明可以分页
            if (Objects.equals(respData.getHasMore(), Boolean.TRUE)) {
                pageToken = respData.getPageToken();
                list.add(respData.getItems());
                // 处理数据
            } else {
                hasMore = false;
                // 处理数据
                list.add(respData.getItems());
            }
        }

        while (hasMore) {
            // 构建client
            Client client = Client.newBuilder(appId, appSecret).build();

            // 创建请求对象
            ChildrenDepartmentReq req = ChildrenDepartmentReq.newBuilder()
                    .departmentId(departmentId)
                    .userIdType("open_id")
                    .departmentIdType("open_department_id")
                    .pageSize(pageSize)
                    .pageToken(pageToken)
                    .build();

            // 发起请求
            ChildrenDepartmentResp resp = null;
            try {
                resp = client.contact().department().children(req, RequestOptions.newBuilder()
                        .userAccessToken(userAccessToken)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 处理服务端错误
            assert resp != null;
            if (!resp.success()) {
                System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
                return "error";
            } else {
                ChildrenDepartmentRespBody respData = resp.getData();
                // 如果has_more为true，说明可以分页
                if (Objects.equals(respData.getHasMore(), Boolean.TRUE)) {
                    pageToken = respData.getPageToken();
                    list.add(respData.getItems());
                    for (Department item : respData.getItems()) {
                        departmentId = item.getOpenDepartmentId();
                    }
                    // 处理数据
                } else {
                    hasMore = false;
                    // 处理数据
                    list.add(respData.getItems());
                    for (Department item : respData.getItems()) {
                        departmentId = item.getOpenDepartmentId();
                        if (Objects.nonNull(departmentId)) {

                        }
                    }
                }

            }

        }
        // 业务数据处理
        //return Jsons.DEFAULT.toJson(resp.getData());
        return list;
    }

    private ChildrenDepartmentResp getChildrenDepartment(String departmentId, String pageToken, int pageSize) {
        Client client = Client.newBuilder(appId, appSecret).build();

        // 创建请求对象
        ChildrenDepartmentReq req = ChildrenDepartmentReq.newBuilder()
                .departmentId(departmentId)
                .userIdType("open_id")
                .departmentIdType("open_department_id")
                .pageSize(pageSize)
                .pageToken(pageToken)
                .build();

        // 发起请求
        ChildrenDepartmentResp resp = null;
        try {
            resp = client.contact().department().children(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 获取父部门信息
     *
     * @param departmentId 部门ID 必填
     */
    @GetMapping("/parentDepartmentSample/{departmentId}")
    public Object parentDepartmentSample(@PathVariable(value = "departmentId") String departmentId) {

        // 构建client
        Client client = Client.newBuilder(appId, appSecret).build();

        // 创建请求对象
        ParentDepartmentReq req = ParentDepartmentReq.newBuilder()
                .userIdType("open_id")
                .departmentIdType("department_id")
                //"10001-A"
                .departmentId(departmentId)
                .pageSize(20)
                .build();

        // 发起请求
        ParentDepartmentResp resp = null;
        try {
            resp = client.contact().department().parent(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return "error";
        }

        // 业务数据处理
        return Jsons.DEFAULT.toJson(resp.getData());
    }

    /**
     * 获取单个部门用户信息
     */
    @GetMapping("/getDepartmentSample/{departmentId}")
    public Object getDepartmentSample(@PathVariable(value = "departmentId") String departmentId) {

        GetDepartmentResp resp = getDepartmentResp(departmentId);

        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return "error";
        } else {

        }

        // 业务数据处理
        return Jsons.DEFAULT.toJson(resp.getData());
    }


    private GetDepartmentResp getDepartmentResp(String departmentId) {
        // 构建client
        Client client = Client.newBuilder(appId, appSecret).build();

        // 创建请求对象
        GetDepartmentReq req = GetDepartmentReq.newBuilder()
                //"od-6aa61d5b78ea678fc6ef850a7aa5349d"
                .departmentId(departmentId)
                .userIdType("open_id")
                .departmentIdType("open_department_id")
                .build();
        // 发起请求
        GetDepartmentResp resp = null;
        try {
            resp = client.contact().department().get(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 获取部门直属用户列表
     */
    @GetMapping("/findByDepartmentUserSampleList/{departmentId}")
    public Object findByDepartmentUserSampleList(@PathVariable(value = "departmentId") String departmentId) {
        List<User[]> list = new ArrayList<>();
        Integer pageSize = 10; String pageToken = null;
        FindByDepartmentUserResp resp = findByDepartmentUserSampleList(departmentId, pageSize, pageToken);
        // 处理服务端错误
        if (!resp.success()) {
            System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
            return "error";
        } else {
            list.add(resp.getData().getItems());
            while (resp.getData().getHasMore()) {
                pageToken = resp.getData().getPageToken();
                resp = findByDepartmentUserSampleList(departmentId, pageSize, pageToken);
                if (!resp.success()) {
                    System.out.println(String.format("code:%s,msg:%s,reqId:%s", resp.getCode(), resp.getMsg(), resp.getRequestId()));
                    return "error";
                }
                list.add(resp.getData().getItems());
            }
        }
        // 业务数据处理
        return list;
    }


    public FindByDepartmentUserResp findByDepartmentUserSampleList(String departmentId, Integer pageSize, String pageToken) {
        // 构建client
        Client client = Client.newBuilder(appId, appSecret).build();
        // 创建请求对象
        FindByDepartmentUserReq req = FindByDepartmentUserReq.newBuilder()
                .userIdType("open_id")
                .departmentIdType("open_department_id")
                .departmentId(departmentId)
                .pageSize(pageSize)
                // 当has_more为true时候，代表分页，可以用page_size和page_token进行分页查询
                .pageToken(pageToken)
                .build();

        // 发起请求
        FindByDepartmentUserResp resp = null;
        try {
            resp = client.contact().user().findByDepartment(req, RequestOptions.newBuilder()
                    .userAccessToken(userAccessToken)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

}
