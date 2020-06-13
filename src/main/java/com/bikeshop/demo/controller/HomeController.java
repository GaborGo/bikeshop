package com.bikeshop.demo.controller;

import com.bikeshop.demo.service.ItemService;
import com.bikeshop.demo.service.SearchService;
import com.bikeshop.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;
    private ItemService itemService;
    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService) { this.searchService = searchService; }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public String getHome(Model model){
        model.addAttribute("items", itemService.getAllActiveItems());
        return "home";
    }

    //sorting items + sorting search results in case
    @RequestMapping("sort")
    public String indexWithQuery(@RequestParam("query")String query, @RequestParam("field") String field, Model model) {
        if(query.equals("")){
        switch (field) {
            case "name":
                model.addAttribute("items", searchService.sortAllByName());
                break;
            case "category":
                model.addAttribute("items", searchService.sortAllByCategory());
                break;
            case "brand":
                model.addAttribute("items", searchService.sortAllByBrand());
                break;
            case "recent":
                model.addAttribute("items", searchService.sortAllByIdDesc());
                break;
            case "priceAsc":
                model.addAttribute("items", searchService.sortAllByPriceAsc());
                break;
            case "priceDesc":
                model.addAttribute("items", searchService.sortAllByPriceDesc());
                break;
        }}
        else {
            switch (field) {
                case "name":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByName(query));
                    break;
                case "category":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByCategory(query));
                    break;
                case "brand":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByBrand(query));
                    break;
                case "priceAsc":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByPriceAsc(query));
                    break;
                case "priceDesc":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByPriceDesc(query));
                    break;
                case "recent":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByIdDesc(query));
            }
        }
        model.addAttribute("field", field);
        model.addAttribute("query", query);

        return "home";
    }

    @GetMapping("search")
    public String navSearch(@RequestParam("query") String query, Model model){
        model.addAttribute("items", searchService.getSearchedItems(query));
        model.addAttribute("query", query);     //sending back query string - needed for sorting searched items
        return "home";
    }

}
