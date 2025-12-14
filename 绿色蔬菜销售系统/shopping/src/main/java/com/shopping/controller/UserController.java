package com.shopping.controller;

import com.shopping.domain.dto.req.UserReqDto;
import com.shopping.domain.dto.res.UserResDto;
import com.shopping.service.UserService;
import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.ResultDTO;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Data
@RestController
@Api(tags = "user")
@RequestMapping("/user")
@SuppressWarnings("unchecked")
public class UserController {

    private final UserService userService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"user"}, nickname = "addUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<UserResDto> addUser(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody UserReqDto reqDto) {
        UserResDto resDto = userService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"user",}, nickname = "deleteUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteUser(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = userService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "user",}, nickname = "getUserList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-password-eq", value = "密码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-nickName-eq", value = "昵称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-phone-eq", value = "手机号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-age-eq", value = "年龄", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-address-eq", value = "地址", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-sex-eq", value = "性别，1 男 2 女", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<UserResDto>> getUserList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<UserResDto> resDtoPager = userService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"user"}, nickname = "getOneUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<UserResDto> getOneUser(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        UserResDto resDto = userService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"user"}, nickname = "getOneUserByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "nickName", value = "昵称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "address", value = "地址", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sex", value = "性别，1 男 2 女", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "host", value = "", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "user", value = "", paramType = "query", dataTypeClass = Map.class),
    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<UserResDto> getOneUser(@ApiIgnore @RequestParam Map<String, Object> params) {
        UserResDto resDto = userService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    @GetMapping(value = "/list", produces = {"application/json"})
    public ResultDTO<List<UserResDto>> getListUser() {
        List<UserResDto> resDto = userService.selectList();
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"user"}, nickname = "updateUserByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "nickName", value = "昵称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "address", value = "地址", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sex", value = "性别，1 男 2 女", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<UserResDto> updateUser(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        userService.updateProps(id, params);
        UserResDto resDto = userService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"user"}, nickname = "patchUpdateUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<UserResDto> patchUpdateUser(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody UserReqDto reqDto) {
        userService.updateProps(id, reqDto);
        UserResDto resDto = userService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"user"}, nickname = "putUpdateUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<UserResDto> putUpdateUser(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody UserReqDto reqDto) {
        userService.updateAllProps(id, reqDto);
        UserResDto resDto = userService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
