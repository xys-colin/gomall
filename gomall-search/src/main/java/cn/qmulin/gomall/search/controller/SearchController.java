package cn.qmulin.gomall.search.controller;

import cn.qmulin.gomall.search.service.MallSearchService;
import cn.qmulin.gomall.search.vo.SearchParam;
import cn.qmulin.gomall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/26 22:34
 */
@Controller
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;
    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request){
        String queryString = request.getQueryString();
        param.set_queryString(queryString);
        SearchResult result= mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
