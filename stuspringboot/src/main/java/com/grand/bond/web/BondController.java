package com.grand.bond.web;

        import com.grand.bond.service.BondService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RestController;

@RestController
public class BondController {

    @Autowired
    BondService bondService;

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public int update(String name){
        return bondService.update(name);
    }
}