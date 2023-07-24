package com.springEx.boilerplate1.repository.mapper;

import com.springEx.boilerplate1.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberMapper {
    Member selectMember(HashMap<String, Object> map);
    List<Member> selectMemberList();
}
