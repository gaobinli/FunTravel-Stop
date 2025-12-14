/*
 Navicat Premium Data Transfer

 Source Server         : Local
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : shopping

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 29/11/2025 17:20:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (28, '农残检测公示 安心选购', '<img src=\"http://localhost:9000/img/b44a5835e06b4e40a6d6575da283032c.png\" style=\"max-width:100%;\" contenteditable=\"false\"/>', 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 15:34:57');
INSERT INTO `announcement` VALUES (29, '当季绿叶菜 全场 8 折特惠', '<img src=\"http://localhost:9000/img/b44a5835e06b4e40a6d6575da283032c.png\" style=\"max-width:100%;\" contenteditable=\"false\"/>', 0, '管理员', '2025-11-29 15:34:32', '管理员', '2025-11-29 15:35:40');
INSERT INTO `announcement` VALUES (30, '周末下单 赠新鲜香葱一把', '<img src=\"http://localhost:9000/img/b44a5835e06b4e40a6d6575da283032c.png\" style=\"max-width:100%;\" contenteditable=\"false\"/>', 0, '管理员', '2025-11-29 15:35:48', '管理员', '2025-11-29 15:35:48');
INSERT INTO `announcement` VALUES (31, '高山娃娃菜 现采现发', '<img src=\"http://localhost:9000/img/b44a5835e06b4e40a6d6575da283032c.png\" style=\"max-width:100%;\" contenteditable=\"false\"/>', 0, '管理员', '2025-11-29 15:35:57', '管理员', '2025-11-29 15:35:57');

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '轮播图名字',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `sort` tinyint(1) NULL DEFAULT NULL COMMENT '排序',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (19, '轮播图', 'http://localhost:9000/img/c4514b8c53894c3e8194514494191894.png', 1, 0, '管理员', '2025-11-29 14:07:26', '早睡', '2025-11-29 15:30:36');
INSERT INTO `carousel` VALUES (20, '轮播图', 'http://localhost:9000/img/df90093c561a4d5a8f191886ffdfa7b6.png', 1, 0, '管理员', '2025-11-29 14:07:26', '早睡', '2025-11-29 15:31:03');
INSERT INTO `carousel` VALUES (21, '轮播图', 'http://localhost:9000/img/1baa2cffc08841f097340d416ef43a9f.png', 1, 0, '管理员', '2025-11-29 14:07:26', '早睡', '2025-11-29 15:31:09');
INSERT INTO `carousel` VALUES (22, '轮播图', 'http://localhost:9000/img/8eae28545e924c6e80fd60dc921e03b0.png', 1, 0, '管理员', '2025-11-29 14:07:26', '早睡', '2025-11-29 15:31:19');

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `sort` tinyint(1) NULL DEFAULT NULL COMMENT '排序',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES (14, '新鲜叶菜', 1, 0, '管理员', '2025-11-29 15:17:23', '管理员', '2025-11-29 15:17:23');
INSERT INTO `module` VALUES (15, '优选根茎', 2, 0, '管理员', '2025-11-29 15:17:33', '管理员', '2025-11-29 15:17:33');

-- ----------------------------
-- Table structure for module_sku
-- ----------------------------
DROP TABLE IF EXISTS `module_sku`;
CREATE TABLE `module_sku`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `module_id` int NULL DEFAULT NULL COMMENT '分类',
  `sku_id` int NULL DEFAULT NULL COMMENT '商品',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of module_sku
-- ----------------------------
INSERT INTO `module_sku` VALUES (121, NULL, 82, 1, '星星', '2025-11-29 15:19:45', '星星', '2025-11-29 15:19:45');
INSERT INTO `module_sku` VALUES (122, NULL, 85, 1, '早睡', '2025-11-29 15:21:29', '早睡', '2025-11-29 15:21:29');
INSERT INTO `module_sku` VALUES (123, 14, 85, 0, '早睡', '2025-11-29 15:22:17', '早睡', '2025-11-29 15:22:17');
INSERT INTO `module_sku` VALUES (124, 14, 84, 0, '早睡', '2025-11-29 15:22:23', '早睡', '2025-11-29 15:22:23');
INSERT INTO `module_sku` VALUES (125, 14, 83, 0, '星星', '2025-11-29 15:22:44', '星星', '2025-11-29 15:22:44');
INSERT INTO `module_sku` VALUES (126, 14, 82, 0, '星星', '2025-11-29 15:22:54', '星星', '2025-11-29 15:22:54');
INSERT INTO `module_sku` VALUES (127, 15, 86, 0, '星星', '2025-11-29 15:24:31', '星星', '2025-11-29 15:24:31');
INSERT INTO `module_sku` VALUES (128, 15, 87, 0, '星星', '2025-11-29 15:24:57', '星星', '2025-11-29 15:24:57');
INSERT INTO `module_sku` VALUES (129, 15, 88, 0, '早睡', '2025-11-29 15:25:26', '早睡', '2025-11-29 15:25:26');
INSERT INTO `module_sku` VALUES (130, 15, 89, 1, '早睡', '2025-11-29 15:25:49', '早睡', '2025-11-29 15:25:49');
INSERT INTO `module_sku` VALUES (131, 15, 89, 0, '早睡', '2025-11-29 15:29:05', '早睡', '2025-11-29 15:29:05');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_id` int NULL DEFAULT NULL COMMENT '店铺',
  `order_no` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `sku_id` int NULL DEFAULT NULL COMMENT 'skuId',
  `number` int NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购买价格',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (107, 81, 'C20251129b3715', 86, 1, 6.00, 0, '', '2025-11-29 16:50:38', '', '2025-11-29 16:50:38');
INSERT INTO `order_detail` VALUES (108, 82, 'C20251129cf4c2', 89, 1, 5.00, 0, '', '2025-11-29 16:50:45', '', '2025-11-29 16:50:45');
INSERT INTO `order_detail` VALUES (109, 80, 'C202511298d82f', 87, 2, 8.00, 0, '', '2025-11-29 16:50:54', '', '2025-11-29 16:50:54');
INSERT INTO `order_detail` VALUES (110, 81, 'C20251129e836f', 86, 1, 6.00, 0, '', '2025-11-29 16:50:54', '', '2025-11-29 16:50:54');
INSERT INTO `order_detail` VALUES (111, 80, 'C20251129c148a', 87, 2, 8.00, 0, '星星', '2025-11-29 17:16:10', '星星', '2025-11-29 17:16:10');
INSERT INTO `order_detail` VALUES (112, 81, 'C2025112934044', 86, 1, 6.00, 0, '星星', '2025-11-29 17:16:10', '星星', '2025-11-29 17:16:10');

-- ----------------------------
-- Table structure for sku
-- ----------------------------
DROP TABLE IF EXISTS `sku`;
CREATE TABLE `sku`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_id` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `price` decimal(10, 0) NULL DEFAULT NULL COMMENT '价格',
  `inventory` int NULL DEFAULT NULL COMMENT '库存量',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '详情',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sku
-- ----------------------------
INSERT INTO `sku` VALUES (82, '80', '生菜', 'http://localhost:9000/img/d45edf0322bb482bad02499c68c94733.png', 4, 999, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '星星', '2025-11-29 15:19:45', '星星', '2025-11-29 15:22:54');
INSERT INTO `sku` VALUES (83, '81', '胡萝卜', 'http://localhost:9000/img/0d381fc3b4b444a899a19bf234bef434.png', 6, 999, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '星星', '2025-11-29 15:20:18', '星星', '2025-11-29 15:22:44');
INSERT INTO `sku` VALUES (84, '82', '上海青', 'http://localhost:9000/img/3fa4a6ee4cff49a58e99e79fae4644ad.png', 3, 999, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '早睡', '2025-11-29 15:20:49', '早睡', '2025-11-29 15:22:22');
INSERT INTO `sku` VALUES (85, '82', '土豆', 'http://localhost:9000/img/c65d6ee57edd46fa8e103c1ce4356d6e.png', 3, 999, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '早睡', '2025-11-29 15:21:29', '早睡', '2025-11-29 15:22:17');
INSERT INTO `sku` VALUES (86, '81', '白萝卜', 'http://localhost:9000/img/0bfe6e8d70bb4bcfbf62218ee4ae1bea.png', 6, 996, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '星星', '2025-11-29 15:24:31', '星星', '2025-11-29 17:16:10');
INSERT INTO `sku` VALUES (87, '80', '山药', 'http://localhost:9000/img/906320a5fc374cc28ae57c2ebd9de977.png', 8, 9995, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '星星', '2025-11-29 15:24:57', '星星', '2025-11-29 17:16:10');
INSERT INTO `sku` VALUES (88, '82', '莲藕', 'http://localhost:9000/img/6217827ac3454e4fbd370a6ceb8593fe.png', 6, 999, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '早睡', '2025-11-29 15:25:26', '早睡', '2025-11-29 15:25:26');
INSERT INTO `sku` VALUES (89, '82', '红薯', 'http://localhost:9000/img/ab12a72ce84646989f0490897c5677cd.png', 5, 998, '<p><img src=\"http://localhost:9000/img/208445cb09214e04811f0e6e1560cbde.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 0, '早睡', '2025-11-29 15:25:49', '早睡', '2025-11-29 16:50:45');

-- ----------------------------
-- Table structure for sku_appraise
-- ----------------------------
DROP TABLE IF EXISTS `sku_appraise`;
CREATE TABLE `sku_appraise`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sku_id` int NOT NULL COMMENT '商品',
  `user_id` int NOT NULL COMMENT '评价人',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评价内容',
  `logistice_appraise` tinyint(1) NULL DEFAULT NULL COMMENT '物流评分',
  `order_no` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `sku_appraise` tinyint(1) NULL DEFAULT NULL COMMENT '商品评价',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sku_appraise
-- ----------------------------
INSERT INTO `sku_appraise` VALUES (70, 87, 130, '太棒啦！！！', 5, 'C202511298d82f', 5, '星星', '2025-11-29 16:52:21', '星星', '2025-11-29 16:52:21', 0);
INSERT INTO `sku_appraise` VALUES (71, 87, 130, '哇哈哈哈哈哈哈！！！', 5, 'C20251129c148a', 5, '星星', '2025-11-29 17:17:31', '星星', '2025-11-29 17:17:31', 0);

-- ----------------------------
-- Table structure for sku_order
-- ----------------------------
DROP TABLE IF EXISTS `sku_order`;
CREATE TABLE `sku_order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `store_id` int NULL DEFAULT NULL COMMENT '店铺',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `order_no` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `status` tinyint NULL DEFAULT NULL COMMENT '订单状态：0-待发货，1-已发货，2-已收货，3-申请退货，4-已退货',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sku_order
-- ----------------------------
INSERT INTO `sku_order` VALUES (88, 81, '13301867905', 'C20251129b3715', '2025-11-29 16:51:35', '2025-11-29 16:51:45', 4, 0, '', '2025-11-29 16:50:37', '星星', '2025-11-29 16:52:00');
INSERT INTO `sku_order` VALUES (89, 82, '13301867905', 'C20251129cf4c2', NULL, NULL, 0, 0, '', '2025-11-29 16:50:45', '', '2025-11-29 16:50:45');
INSERT INTO `sku_order` VALUES (90, 80, '13301867905', 'C202511298d82f', '2025-11-29 16:51:34', '2025-11-29 16:51:44', 2, 0, '', '2025-11-29 16:50:54', '星星', '2025-11-29 16:51:43');
INSERT INTO `sku_order` VALUES (91, 81, '13301867905', 'C20251129e836f', '2025-11-29 16:51:35', '2025-11-29 16:51:45', 2, 0, '', '2025-11-29 16:50:54', '星星', '2025-11-29 16:51:44');
INSERT INTO `sku_order` VALUES (92, 80, '13301867905', 'C20251129c148a', '2025-11-29 17:17:05', '2025-11-29 17:17:19', 2, 0, '星星', '2025-11-29 17:16:10', '星星', '2025-11-29 17:17:18');
INSERT INTO `sku_order` VALUES (93, 81, '13301867905', 'C2025112934044', NULL, NULL, 0, 0, '星星', '2025-11-29 17:16:10', '星星', '2025-11-29 17:16:10');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchants_code` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商家',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺封面',
  `simple_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺简介',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (80, 'nova', '禾田鲜蔬', 'http://localhost:9000/img/c4adcd24001b4c33b1410ba6fefdd326.png', '主打应季蔬菜、产地直供的店铺', 0, '星星', '2025-11-29 15:12:15', '星星', '2025-11-29 15:12:15');
INSERT INTO `store` VALUES (81, 'nova', '家厨鲜蔬铺', 'http://localhost:9000/img/49a19585fae547c1ae98c9e03002efa3.png', '以组合蔬菜包、配菜套餐为核心产品的店铺', 0, '星星', '2025-11-29 15:13:44', '星星', '2025-11-29 15:13:44');
INSERT INTO `store` VALUES (82, 'bed', '沐野鲜蔬', 'http://localhost:9000/img/1829bbd0499f49198aaf04b8b41674ea.png', '主打无农残、生态种植的店铺，传递 “源头直供、新鲜存储”', 0, '早睡', '2025-11-29 15:16:06', '早睡', '2025-11-29 15:16:06');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级菜单',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` tinyint(1) NULL DEFAULT NULL COMMENT '排序',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (11, 'Home', '首页', '', '/admin/home', 'HomeFilled', 99, 0, '1', '2025-11-29 14:07:26', '3', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (12, 'SysUser', '系统用户', 'Sys', '/admin/sysUser', 'UserFilled', 3, 0, '1', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (13, 'SysRole', '系统角色', 'Sys', '/admin/SysRole', 'Place', 2, 0, '1', '2025-11-29 14:07:26', '3', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (14, 'Sys', '系统设置', NULL, NULL, 'Setting', 1, 0, NULL, '2025-11-29 14:07:26', NULL, '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (15, 'SysMenu', '系统菜单', 'Sys', '/admin/SysMenu', 'Menu', 1, 0, NULL, '2025-11-29 14:07:26', NULL, '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (29, 'Carousel', '轮播图管理', '', '/admin/carousel', 'View', 55, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (30, 'Module', '商品分类管理', '', '/admin/module', 'Tickets', 66, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (31, 'Sku', '商品管理', '', '/admin/sku', 'MagicStick', 77, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (32, 'Order', '订单管理', '', '/admin/order', 'ShoppingBag', 44, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (33, 'User', '用户管理', '', '/admin/user', 'User', 88, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (34, 'SkuAppraise', '商品评论管理', '', '/admin/skuAppraise', 'DocumentChecked', 33, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (35, 'Merchants', '商家管理', '', '/admin/merchants', 'User', 85, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (36, 'Store', '店铺管理', '', '/admin/store', 'Tickets', 80, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');
INSERT INTO `sys_menu` VALUES (37, 'Announcement', '系统公告', '', '/admin/announcement', 'Tickets', 22, 0, '管理员', '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '001', '商家', 0, NULL, '2025-11-29 14:07:26', '管理员', '2025-11-29 14:07:26');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `menu_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 229 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (195, '222', 'Sys', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (196, '222', 'SysMenu', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (197, '222', 'Home', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (198, '222', 'SysRole', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (214, '001', 'Home', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (215, '001', 'SysRole', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (216, '001', 'Sys', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (217, '9527', 'Home', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (218, '9527', 'SysUser', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (219, '9527', 'Sys', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (220, '001', 'Home', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (221, '001', 'Sys', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (222, '001', 'Carousel', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (223, '001', 'Module', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (224, '001', 'Home', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (225, '001', 'Sys', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (226, '001', 'Carousel', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (227, '001', 'Module', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (228, '001', 'Sku', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (229, '001', 'Home', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (230, '001', 'Sys', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (231, '001', 'Carousel', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (232, '001', 'Module', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (233, '001', 'Sku', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (234, '001', 'Store', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (235, '001', 'Home', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (236, '001', 'Sys', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (237, '001', 'Carousel', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (238, '001', 'Module', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (239, '001', 'Sku', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (240, '001', 'Store', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (241, '001', 'Order', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (242, '001', 'SkuAppraise', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_role_menu` VALUES (243, '001', 'Home', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (244, '001', 'Sys', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (245, '001', 'Carousel', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (246, '001', 'Sku', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (247, '001', 'Order', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (248, '001', 'SkuAppraise', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (249, '001', 'Store', 1, '管理员', '2025-11-29 15:32:05', '管理员', '2025-11-29 15:32:05');
INSERT INTO `sys_role_menu` VALUES (250, '001', 'Home', 0, '管理员', '2025-11-29 17:18:50', '管理员', '2025-11-29 17:18:50');
INSERT INTO `sys_role_menu` VALUES (251, '001', 'Sys', 0, '管理员', '2025-11-29 17:18:50', '管理员', '2025-11-29 17:18:50');
INSERT INTO `sys_role_menu` VALUES (252, '001', 'Sku', 0, '管理员', '2025-11-29 17:18:50', '管理员', '2025-11-29 17:18:50');
INSERT INTO `sys_role_menu` VALUES (253, '001', 'Order', 0, '管理员', '2025-11-29 17:18:50', '管理员', '2025-11-29 17:18:50');
INSERT INTO `sys_role_menu` VALUES (254, '001', 'SkuAppraise', 0, '管理员', '2025-11-29 17:18:50', '管理员', '2025-11-29 17:18:50');
INSERT INTO `sys_role_menu` VALUES (255, '001', 'Store', 0, '管理员', '2025-11-29 17:18:50', '管理员', '2025-11-29 17:18:50');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '账号是否启用，1 启用',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$UIjFCcU79qZ/qaF2/D3kPeSuFrLvbEMbzxAozHFx3Xv1BV/MGEDny', '管理员', '2131', '123123', 1, 0, NULL, '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_user` VALUES (42, 'nova', '$2a$10$wYjnT5n0x35rmCS1zfXvbO0LZ.Ia1I4.IblseD/3zJfxeFSeptj36', '星星', '13533344444', '', 1, 0, '', '2025-11-29 15:07:47', '管理员', '2025-11-29 15:08:07');
INSERT INTO `sys_user` VALUES (43, 'bed', '$2a$10$IgxV2czlFLrS/AmGc4Qa8.Lx9.nw2uNDTYHf0JkggrR8s9WGpOj/S', '早睡', '1343345656', '', 1, 0, '', '2025-11-29 15:14:40', '管理员', '2025-11-29 15:14:56');
INSERT INTO `sys_user` VALUES (44, 'haha', '$2a$10$pzKx/pzOZV9d6tQ1DhQY1.aMbGYgcxQQvXblPhRngo7ZBlBXFt5ua', '哈哈', '1324443444', '', 1, 0, '', '2025-11-29 17:19:10', '管理员', '2025-11-29 17:19:28');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
  `role_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 'admin', '001', 1, NULL, '2025-11-29 14:07:27', NULL, '2025-11-29 14:07:27');
INSERT INTO `sys_user_role` VALUES (10, 'zhagnsan', '001', 1, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_user_role` VALUES (11, 'zhagnsan', '001', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_user_role` VALUES (12, '1', '001', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_user_role` VALUES (13, '2', '001', 0, '管理员', '2025-11-29 14:07:27', '管理员', '2025-11-29 14:07:27');
INSERT INTO `sys_user_role` VALUES (14, 'nova', '001', 0, '管理员', '2025-11-29 15:08:08', '管理员', '2025-11-29 15:08:08');
INSERT INTO `sys_user_role` VALUES (15, 'bed', '001', 0, '管理员', '2025-11-29 15:14:56', '管理员', '2025-11-29 15:14:56');
INSERT INTO `sys_user_role` VALUES (16, 'haha', '001', 0, '管理员', '2025-11-29 17:19:27', '管理员', '2025-11-29 17:19:27');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别，1 男 2 女',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint NULL DEFAULT NULL COMMENT '是否启用，1启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10417 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (130, '$2a$10$gV5.cZXvxtbbY9yzlfuEJumAIsrYD0rTPSVhKZWxft9tZilX/aKfG', '帅振鸣', '13301867905', 51, '惠民街127号-10-6', 2, 0, '', '2025-11-29 14:07:27', '星星', '2025-11-29 17:08:28', 1);
INSERT INTO `user` VALUES (131, '$2a$10$f6jkgzEXNebfgJ90LQ.43uaSO/xGZpZJQQ3U.XEydr1K9k/0RPZve', '裴媛', '15107617116', 66, '扬州街74号-6-7', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (132, '$2a$10$48hNpnJRqjNg7vG2M8bNEu4ejNWTdQmXYlJZ2J3X9QC.KsLKXuz0.', '芮雪', '13203040497', 30, '珠海一广场122号-14-8', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (133, '$2a$10$d8/77iAC2pchNuKzguNLSeF5LJCG2QyfAQ7JlHqn4CeC8qSgJO4U.', '匡淑梅', '15608316906', 15, '南九水街131号-1-8', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (134, '$2a$10$f4OzbsbM.kjAVSiPPEsjP.WoQjPSbTlVoQrxYmGsTzV6LOGb9hQpa', '年芸', '15902622335', 29, '冠县路133号-15-8', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (135, '$2a$10$jXsHL6znsl3m6wMN43u5WOZlFsIf.xHdzkzy69UWuKEYP1kzph8Ke', '赖榕福', '15807195125', 67, '石村广场39号-6-3', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (136, '$2a$10$Z7hq.3c8fp0WSTaxD9u.keOXZg0VCBuzkpF4R.DWmFLblXov2/dyC', '国德榕', '13406147032', 48, '丽水街86号-3-10', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (137, '$2a$10$V92r1JO.s/X/M6IRx7RJ4.9gW87V7FpGwpHznlzcDz.IcU8b4xi4C', '寇致', '15707694915', 29, '国城路130号-7-2', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (138, '$2a$10$W6Nk4DS32Bd/AFypSxnEzOYUO.SfG2scEtWWdXpFxvaI9CN6cWL6m', '佘岚', '13704047122', 53, '紫荆关街71号-17-1', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (139, '$2a$10$lDIoDz/J.4iGobVJhUyFw.N.l03JGkO8uMj7I4wNIfE/tJB6rBGvO', '索群', '13708276702', 45, '四方街27号-2-1', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (140, '$2a$10$aj5/Gtp/RY3s21CQqTKJBeVXw5Q/Ju5aUh1TuWyTkrEb3WyBqx80S', '祝璧舒', '15901708138', 26, '东山路120号-18-7', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (141, '$2a$10$12q17GGNMDAm3qfUK0UBtuENPeyCuCJtbevuk2/8qddrK7kR./qt2', '蒙黛', '13108804694', 43, '港青街144号-10-4', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (142, '$2a$10$ho1joxyZPxxql6rfpJIKqezuSkwtyZLTvbN3HlZuYqUlyFhKl1Kp2', '边菲娥', '15604132817', 50, '益都街83号-5-1', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (143, '$2a$10$D25phKk8Bkby39M.iOunP.iLy0uDYXAuCgRvLELe5UqDZ2DcY/GZa', '台德', '15003738435', 67, '热河大厦54号-8-2', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (144, '$2a$10$ZWLXx64xk1gYOSxSeqAuQ.n2rk0ULpr.J.E2kKZ05ZxUsQt8Y1A3q', '阳学家', '15507501270', 30, '仙山西大厦18号-4-2', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (145, '$2a$10$H1YeZFBmRHZ8Bvi/9R3HgO0NS422HAVTkkBP.dFUNlja9QokZIwA6', '赵世', '13706687329', 70, '高雄广场139号-20-2', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (146, '$2a$10$TEW3QHOHn.czsyh/kOoq..F2L4n9xQb41iZxGdjqMJFUjrg8ZgyWe', '连玲霞', '13004968320', 54, '新泰大厦142号-9-4', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (147, '$2a$10$yudxMo0ohK1dZYCypnB1P.ewCszoTnnVZd0M5Rat07s54q6fNof1O', '叔雅冰', '13700463852', 50, '吴县二大厦65号-6-7', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (148, '$2a$10$USv6ktEqQZYhzVT2t2E3..WhS2qzDVqV5J8j9ylpC3eHijwgYjLYm', '杜毅', '13006892916', 16, '江苏广场74号-10-9', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (149, '$2a$10$EGcBHHe82rU5Emd6AfnxIukelxBGamc7LJywFmMH.OXGY6jG5XhaW', '牛仪', '13905470967', 19, '观海二广场118号-19-7', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (150, '$2a$10$Q3hUxoac1b4gtrCm.Tl1keb31dHdTq4gs9Jy8v76/isX1rcThVJ0K', '巩丹', '15307973738', 21, '江宁广场42号-17-9', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (151, '$2a$10$Qljkh5deMcIzAFyA95WUBOYLuNAeBzdSN4F/AGANzbtJ.LVe7k2nW', '贾龙乐', '15600387564', 63, '芙蓉路116号-13-6', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (152, '$2a$10$trokjkMd04bkyysQ6yIni.SHNFlLOg.wd2jB5f7mIZKPCKdKtSyuW', '乐达', '13904176792', 70, '沈阳支大厦103号-19-1', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (153, '$2a$10$PFaUD49I3i/NDkoBQMhWl.YNOiT2AQj6uAc0UE/ehsRllcrKUs/wO', '端策', '13900021995', 67, '港青街117号-14-10', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (154, '$2a$10$ebs1ZT.5GLVSwX1RZfH6luqBtfAgSMut0mgs8QwDErKTuxgJOgptK', '政静云', '13504826407', 34, '天台东二街62号-8-8', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (155, '$2a$10$EIm22jRABkkIRxtGwsH78eNL/qJmb8tdvj/ynbJnR35f8TCOuolxe', '曹弘', '15702178450', 16, '嘉祥路123号-19-9', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (156, '$2a$10$lGwJ06o/rUxblR7BCNTbxu.hzeAACxssa3KQ1FXNMwLOiPEqNml8W', '靳亚', '13802046911', 20, '大港纬二路20号-11-8', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (157, '$2a$10$lbqSMOVIZMoYXxsUQwYpv.5xTQmW6oLYgS5nBqVIgY5agB0Mdv.UW', '沈全', '13101236440', 31, '屏东广场76号-18-8', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (158, '$2a$10$3bxyj1u/RXyR2KUrBM5blOR.bPcMSmdfppbwt4R2zboZr051SNn5S', '竺英妍', '15002772771', 67, '城阳街70号-19-10', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (159, '$2a$10$Detel/b5m.vO48Sj5AE65uQ.OVysiAhQn7lSQBqYDhTJCUWslE/WC', '路英聪', '15704615439', 65, '新湛路35号-17-4', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (160, '$2a$10$jvAnDXH4cU0Ws489kHQlTu0wdMAMd0ZXdtSSoipmJTiUug4CWNtoO', '杜柔芬', '15700540141', 15, '平定路147号-14-9', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (161, '$2a$10$HiKyVATcYQht4CD.dqKyletdem6wDR3xSj04io6hsQJm6p.XlhQfS', '曾翔时', '15907585687', 42, '南九水街125号-9-3', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (162, '$2a$10$OzpArx71U6au0G6IuZD9YeGule5DpSB.ABNVV7gfs3Y.ocUQnqXD6', '叔致', '13806142450', 62, '济南街96号-3-1', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (163, '$2a$10$f0d3i8yifd11jvuljWDrYudLKzN/K1681lLYpFWpvbULnW7xUyTim', '有启', '13205816552', 26, '天台东一路43号-1-8', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (164, '$2a$10$ac1PumwFZdmsLrckgU8rR.BkTWxZkIntLZA06XBWVcVHjZA/9NT8m', '容利', '15102996549', 58, '台东七大厦79号-5-4', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (165, '$2a$10$9K5titSrcFzcz5qXWxkUCu46j6SzyyqM1DO9vUjDa1z1xkNX3oJ8.', '福坚', '15706876343', 69, '太平山19号-3-2', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (166, '$2a$10$kHSpHplcb4GHHudMjhTi9OBzieNaG/B9CWY5DZ4FTvKkZXmwAzFVK', '邓静枫', '13004063731', 53, '延安一街66号-18-5', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (167, '$2a$10$m8yhZCZ6.W83jXzbdCmyAOYA4YG4NPuqdjIR9fMRM/zR1lEE4TcwW', '笪瑗娅', '15700298926', 31, '朝城路80号-18-4', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (168, '$2a$10$1nuTmGbQmq1.D7YsCKHly.3okaO.DgHk.65oLt2lQE51rrOukEScS', '姜怡', '15005128437', 48, '澳门二路148号-5-5', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (169, '$2a$10$Ob5FYbvVzfN128.cmxq9ie4pGg7zs0RWoUpWIyR07Cw5lYR1PjiUq', '桓媛黛', '13204658288', 22, '观象二路18号-2-4', 1, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (170, '$2a$10$zlMDBcZI39q5cuuxRgn6juRRDnjxjf42J8iXvpw2tQHBpfkqdY.O.', '干永国', '13506256470', 38, '顺城路11号-10-1', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (171, '$2a$10$B7FtFQ/SSxPdu0RSWIr.0e9kvvq/u8q3msxVL4a.w5lzk6rq3PI/m', '文巧', '15800695278', 16, '崇阳街115号-2-1', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (172, '$2a$10$uw4syBrhXVXjYipK6Gf5uuDk9v5utdXBBZX5y7gWQNkX.y3ac5U02', '梁会朗', '15307424077', 26, '上海支路114号-18-3', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (173, '$2a$10$RhB/TU74BujkwqSWfazpeec9foLLBN3pDGSOKOGvb7fpnW4B6qV0O', '益磊', '13707317076', 70, '大港三街32号-10-3', 2, 0, '', '2025-11-29 14:07:27', '', '2025-11-29 14:07:27', 1);
INSERT INTO `user` VALUES (10417, '$2a$10$KMVVJ7kynBEE6WP/ayC/qOeW3vbD39gLhUQfoOuHydsE8Jark8cdy', '测试账号', '134544444444', 222, '测试地址', 1, 0, '管理员', '2025-11-29 14:13:03', '管理员', '2025-11-29 14:15:12', 1);
INSERT INTO `user` VALUES (10418, '$2a$10$ytR4/drdzkGd8VKKIzogi.HReVV7RXAHIL2IqogWsHd98MmgevnM6', '注册测试', '13599948598', 1, '啦啦啦啦啦', 1, 0, '星星', '2025-11-29 17:03:32', '星星', '2025-11-29 17:03:32', 1);

SET FOREIGN_KEY_CHECKS = 1;
