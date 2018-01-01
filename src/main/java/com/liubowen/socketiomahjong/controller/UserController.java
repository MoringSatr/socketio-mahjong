package com.liubowen.socketiomahjong.controller;

import com.liubowen.socketiomahjong.entity.UserInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")
/**
 * 通过这里配置使下面的映射都在/users下，可去除
 * */
public class UserController {

    static Map<Long, UserInfo> users = Collections.synchronizedMap(new HashMap<Long, UserInfo>());

    @ApiOperation(value = "获取用户列表", notes = "")
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public List<UserInfo> getUserList() {
        List<UserInfo> r = new ArrayList<UserInfo>(users.values());
        return r;
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "userInfo", value = "用户详细实体user", required = true, dataType = "UserInfo")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postUser(@RequestBody UserInfo userInfo) {
        users.put(userInfo.getUserId(), userInfo);
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserInfo getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userInfo", value = "用户详细实体user", required = true, dataType = "UserInfo")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody UserInfo userInfo) {
        UserInfo u = users.get(id);
        u.setName(userInfo.getName());
        users.put(id, u);
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }
}