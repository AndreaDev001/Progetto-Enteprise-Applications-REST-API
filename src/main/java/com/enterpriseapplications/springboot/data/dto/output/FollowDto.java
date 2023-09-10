package com.enterpriseapplications.springboot.data.dto.output;

import com.enterpriseapplications.springboot.data.dto.output.refs.UserRef;

import java.time.LocalDate;

public class FollowDto
{
    private UserRef follower;
    private UserRef followed;
    private LocalDate createdDate;
}
