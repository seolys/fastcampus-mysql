package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberWriteService memberWriteService;
	private final MemberReadService memberReadService;

	@GetMapping("/members/{id}")
	public MemberDto register(@PathVariable final Long id) {
		return memberReadService.getMember(id);
	}

	@PostMapping("/members")
	public MemberDto register(@RequestBody final RegisterMemberCommand command) {
		return memberWriteService.register(command);
	}

}
