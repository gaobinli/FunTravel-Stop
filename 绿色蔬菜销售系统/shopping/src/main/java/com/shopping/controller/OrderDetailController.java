package com.shopping.controller;

import com.shopping.domain.dto.req.OrderDetailReqDto;
import com.shopping.domain.dto.res.OrderDetailResDto;
import com.shopping.service.OrderDetailService;
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
@Api(tags = "orderDetail")
@RequestMapping("/orderDetail")
@SuppressWarnings("unchecked")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"orderDetail"}, nickname = "addOrderDetail")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<OrderDetailResDto> addOrderDetail(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody OrderDetailReqDto reqDto) {
        OrderDetailResDto resDto = orderDetailService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"orderDetail",}, nickname = "deleteOrderDetail")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteOrderDetail(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = orderDetailService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "orderDetail",}, nickname = "getOrderDetailList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-orderNo-eq", value = "订单编号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-skuId-eq", value = "skuId", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-number-eq", value = "数量", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<OrderDetailResDto>> getOrderDetailList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<OrderDetailResDto> resDtoPager = orderDetailService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    @ApiOperation(value = "查询订单明细", tags = {"orderDetail",}, nickname = "getDetailList")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/list", produces = {"application/json"})
    public ResultDTO<List<OrderDetailResDto>> getDetailList(@RequestParam String orderNo,
                                                            @RequestParam(required = false) Integer userId) {
        List<OrderDetailResDto> list = orderDetailService.getList(orderNo, userId);
        return ResultDTO.ok(list);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"orderDetail"}, nickname = "getOneOrderDetail")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<OrderDetailResDto> getOneOrderDetail(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        OrderDetailResDto resDto = orderDetailService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"orderDetail"}, nickname = "getOneOrderDetailByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "skuId", value = "skuId", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "number", value = "数量", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<OrderDetailResDto> getOneOrderDetail(@ApiIgnore @RequestParam Map<String, Object> params) {
        OrderDetailResDto resDto = orderDetailService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"orderDetail"}, nickname = "updateOrderDetailByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "skuId", value = "skuId", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "number", value = "数量", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<OrderDetailResDto> updateOrderDetail(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        orderDetailService.updateProps(id, params);
        OrderDetailResDto resDto = orderDetailService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"orderDetail"}, nickname = "patchUpdateOrderDetail")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<OrderDetailResDto> patchUpdateOrderDetail(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody OrderDetailReqDto reqDto) {
        orderDetailService.updateProps(id, reqDto);
        OrderDetailResDto resDto = orderDetailService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"orderDetail"}, nickname = "putUpdateOrderDetail")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<OrderDetailResDto> putUpdateOrderDetail(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody OrderDetailReqDto reqDto) {
        orderDetailService.updateAllProps(id, reqDto);
        OrderDetailResDto resDto = orderDetailService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
