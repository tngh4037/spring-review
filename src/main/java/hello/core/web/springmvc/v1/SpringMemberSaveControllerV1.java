package hello.core.web.springmvc.v1;

import hello.core.domain.member.Member;
import hello.core.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberSaveControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members/save")
    public ModelAndView process(HttpServletRequest request) {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("username"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model에 데이터 보관
        ModelAndView mav = new ModelAndView("save-result");
        mav.getModel().put("member", member); // = mav.addObject("member", member);

        return mav;
    }
}
