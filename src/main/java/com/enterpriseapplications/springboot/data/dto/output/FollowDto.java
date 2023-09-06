package com.enterpriseapplications.springboot.data.dto.output;

import java.time.LocalDate;

public class FollowDto
{
    private UserRef follower;
    private UserRef followed;
    private LocalDate createdDate;
}
