package com.lzh.vote.entity.req;

import lombok.Data;

@Data
public class SendMailReq {

    /**
     * 选举场次id
     */
    private Integer roundId;
}
