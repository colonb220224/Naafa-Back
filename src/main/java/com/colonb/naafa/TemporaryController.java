package com.colonb.naafa;

import com.colonb.naafa.hospital.HospitalService;
import kotlin.RequiresOptIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class TemporaryController {

    private final HospitalService hospitalService;

    @RequestMapping("{var}")
    String tempMapping1(@PathVariable String var){
        return "user/"  +var;
    }

    @RequestMapping("admin/{var}")
    String tempMapping2(@PathVariable String var){
        return "admin/"+var;
    }

    @GetMapping("hospital/{seq}")
    public String getHospitalList(@PathVariable long seq, Model model) {

//        List<HashMap<String, Object>> res = hospitalService.getHospitalDetail(seq);

//        model.addAttribute("res", res);

        return "user/hospital";
    }

    @GetMapping("search_hospital")
    public String getHospitalList(Model model) {

        List<HashMap<String, Object>> res = hospitalService.getHospitalList();

        model.addAttribute("res", res);

        return "user/search_hospital";
    }

    @GetMapping("")
    public String main(Model model) {

        List<HashMap<String, Object>> res = hospitalService.getHospitalList();

        model.addAttribute("res", res);

        return "index";
    }
}
