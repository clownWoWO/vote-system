CREATE DATABASE `vote-system` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

CREATE TABLE `t_candidate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL COMMENT '名字',
  `create_user` varchar(25) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='候选人信息';

INSERT INTO `vote-system`.`t_candidate`(`id`, `name`, `create_user`, `create_time`) VALUES (1, '张三', 'admin', '2022-11-25 15:23:06');
INSERT INTO `vote-system`.`t_candidate`(`id`, `name`, `create_user`, `create_time`) VALUES (2, '李四', 'admin', '2022-11-25 15:23:35');

CREATE TABLE `t_round_candidate_conn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(11) DEFAULT NULL COMMENT '候选人id',
  `round_id` int(11) NOT NULL COMMENT '候选场次id',
  `deleted` int(11) DEFAULT '0' COMMENT '0：未删除 1：已删除',
  `create_user` varchar(25) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `t_candidate_id__index` (`candidate_id`) COMMENT '候选人id索引',
  KEY `t_round_id__index` (`round_id`) COMMENT '选举场次id'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='候选人与选举场次关联表';

INSERT INTO `vote-system`.`t_round_candidate_conn`(`id`, `candidate_id`, `round_id`, `deleted`, `create_user`, `create_time`) VALUES (1, 1, 1, 0, 'admin', '2022-11-25 16:51:17');
INSERT INTO `vote-system`.`t_round_candidate_conn`(`id`, `candidate_id`, `round_id`, `deleted`, `create_user`, `create_time`) VALUES (2, 2, 1, 0, 'admin', '2022-11-25 16:51:17');


CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `id_card` varchar(25) DEFAULT NULL COMMENT '身份证',
  `email` varchar(25) DEFAULT NULL COMMENT '邮箱',
  `is_admin` tinyint(4) NOT NULL COMMENT '0：普通用户 1：管理员',
  `status` int(11) DEFAULT '0' COMMENT '0：未停用 1：已停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

INSERT INTO `vote-system`.`t_user`(`id`, `user_name`, `password`, `id_card`, `email`, `is_admin`, `status`) VALUES (1, 'admin', 'YWRtaW4=', NULL, '798816921@qq.com', 1, 0);
INSERT INTO `vote-system`.`t_user`(`id`, `user_name`, `password`, `id_card`, `email`, `is_admin`, `status`) VALUES (2, '李一', 'MTIzNDU2', 'M688108(1)', '798816921@qq.com', 0, NULL);
INSERT INTO `vote-system`.`t_user`(`id`, `user_name`, `password`, `id_card`, `email`, `is_admin`, `status`) VALUES (3, '王二', 'MTIzNDU2', 'M688108(1)', '798816921@qq.com', 0, 0);


CREATE TABLE `t_user_vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `round_id` int(11) DEFAULT NULL COMMENT '选举场次id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `candidate_id` int(11) DEFAULT NULL COMMENT '选举人id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  KEY `t_candidatd_id__index` (`candidate_id`) COMMENT '候选人Id索引',
  KEY `t_round_id__index` (`round_id`) COMMENT '候选场次索引',
  KEY `t_user_id__index` (`user_id`) COMMENT '用户id索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户投票表';

INSERT INTO `vote-system`.`t_user_vote`(`id`, `round_id`, `user_id`, `candidate_id`, `create_time`, `update_time`, `deleted`) VALUES (1, 1, 2, 1, '2022-11-25 18:47:28', NULL, 0);
INSERT INTO `vote-system`.`t_user_vote`(`id`, `round_id`, `user_id`, `candidate_id`, `create_time`, `update_time`, `deleted`) VALUES (2, 1, 1, 2, '2022-11-25 19:52:13', NULL, 0);

CREATE TABLE `t_vote_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(11) DEFAULT NULL COMMENT '候选人id',
  `round_id` int(11) DEFAULT NULL COMMENT '选举场次id',
  `vote_count` int(11) DEFAULT NULL COMMENT '获票数',
  PRIMARY KEY (`id`),
  KEY `t_candidate_id__index` (`candidate_id`) COMMENT '候选人id索引',
  KEY `t_round_id__index` (`round_id`) COMMENT '场次id索引'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='选举结果表';

INSERT INTO `vote-system`.`t_vote_result`(`id`, `candidate_id`, `round_id`, `vote_count`) VALUES (1, 2, 1, 1);
INSERT INTO `vote-system`.`t_vote_result`(`id`, `candidate_id`, `round_id`, `vote_count`) VALUES (2, 1, 1, 1);

CREATE TABLE `t_vote_round` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `round_name` varchar(255) DEFAULT NULL COMMENT '选举场次名称',
  `status` tinyint(4) DEFAULT '0' COMMENT '0：选举未开始；1：选举进行中 2：选举已结束',
  `create_user` varchar(25) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(25) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='选举场次表';

INSERT INTO `vote-system`.`t_vote_round`(`id`, `round_name`, `status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (1, '第一次选举', 2, 'admin', '2022-11-25 16:51:17', 'admin', '2022-11-28 00:50:24');
INSERT INTO `vote-system`.`t_vote_round`(`id`, `round_name`, `status`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES (2, '第二次选举', 0, 'admin', '2022-11-25 17:04:54', NULL, NULL);

