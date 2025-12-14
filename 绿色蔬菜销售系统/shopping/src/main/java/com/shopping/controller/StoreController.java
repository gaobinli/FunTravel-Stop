package com.shopping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.shopping.domain.dto.req.StoreReqDto;
import com.shopping.domain.dto.res.StoreResDto;
import com.shopping.service.StoreService;
import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.ResultDTO;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;
import java.util.List;

@Data
@RestController
@Tag(name = "store")
@RequestMapping("/store")
@SuppressWarnings("unchecked")
public class StoreController {

    private final StoreService storeService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"store"}, nickname = "addStore")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<StoreResDto> addStore(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody StoreReqDto reqDto) {
        StoreResDto resDto = storeService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询所有数据
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "查询所有数据", tags = {"store"}, nickname = "getList")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/list", produces = {"application/json"})
    public ResultDTO<List<StoreResDto>> getList(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<StoreResDto> resDtos = storeService.getList(params);
        return ResultDTO.ok(resDtos);
    }

    @GetMapping(value = "/storeIds/{merchantsCode}", produces = {"application/json"})
    public ResultDTO<String> getStoreIds(@ApiIgnore @PathVariable String merchantsCode) {
        String storeIds = storeService.getStoreIds(merchantsCode);
        return ResultDTO.ok(storeIds);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"store",}, nickname = "deleteStore")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteStore(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = storeService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "store",}, nickname = "getStoreList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-merchantsCode-eq", value = "商家", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-name-eq", value = "产品名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-url-eq", value = "店铺封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-simpleDesc-eq", value = "店铺简介", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-detail-eq", value = "详情", paramType = "query", dataTypeClass = Map.class),

    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<StoreResDto>> getStoreList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<StoreResDto> resDtoPager = storeService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"store"}, nickname = "getOneStore")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<StoreResDto> getOneStore(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        StoreResDto resDto = storeService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"store"}, nickname = "getOneStoreByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantsCode", value = "商家", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "产品名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "url", value = "店铺封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "simpleDesc", value = "店铺简介", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "detail", value = "详情", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<StoreResDto> getOneStore(@ApiIgnore @RequestParam Map<String, Object> params) {
        StoreResDto resDto = storeService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"store"}, nickname = "updateStoreByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantsCode", value = "商家", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "产品名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "url", value = "店铺封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "simpleDesc", value = "店铺简介", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "detail", value = "详情", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<StoreResDto> updateStore(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        storeService.updateProps(id, params);
        StoreResDto resDto = storeService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"store"}, nickname = "patchUpdateStore")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<StoreResDto> patchUpdateStore(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody StoreReqDto reqDto) {
        storeService.updateProps(id, reqDto);
        StoreResDto resDto = storeService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"store"}, nickname = "putUpdateStore")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<StoreResDto> putUpdateStore(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody StoreReqDto reqDto) {
        storeService.updateAllProps(id, reqDto);
        StoreResDto resDto = storeService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
