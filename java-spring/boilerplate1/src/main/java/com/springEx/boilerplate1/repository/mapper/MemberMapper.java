package com.springEx.boilerplate1.repository.mapper;

import com.springEx.boilerplate1.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<Member> selectMember(HashMap<String, Object> map);
    Optional<List<Member>> selectMemberList();
}
