package hello.core.web.springmvc.v2;

import hello.core.domain.member.Member;
import hello.core.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping
    public ModelAndView members() {
        List<Member> members = memberRepository.findAll();

        ModelAndView mav = new ModelAndView("members");
        mav.getModel().put("members", members); // = mav.addObject("members", members);

        return mav;
    }

    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request) {

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
