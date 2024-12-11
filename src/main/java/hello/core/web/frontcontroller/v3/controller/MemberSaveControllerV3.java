package hello.core.web.frontcontroller.v3.controller;

import hello.core.domain.member.Member;
import hello.core.domain.member.MemberRepository;
import hello.core.web.frontcontroller.ModelView;
import hello.core.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("username"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model에 데이터 보관
        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);

        return mv;
    }
}
