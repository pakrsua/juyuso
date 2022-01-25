package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@ApiModel("FrinedRequest")
public class FriendReqDto {
    @ApiModelProperty(name = "유저/신청 ID", example = "id(pk)")
    private String id;

    @ApiModelProperty(name = "유저 별명", example = "search_nick_name")
    private String nickName;

}
