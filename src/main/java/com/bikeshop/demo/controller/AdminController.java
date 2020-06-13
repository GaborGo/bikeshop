package com.bikeshop.demo.controller;


import com.bikeshop.demo.model.Item;
import com.bikeshop.demo.model.ItemImage;
import com.bikeshop.demo.model.User;
import com.bikeshop.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
@RequestMapping("admin")
public class AdminController {

    private ItemService itemService;
    private ItemImageService itemImageService;
    private SearchService searchService;
    private UserService userService;
    private ShoppingCartService shoppingCartService;

    @Autowired
    public void setItemService(ItemService itemService) { this.itemService = itemService; }

    @Autowired
    public void setItemImageService(ItemImageService itemImageService) { this.itemImageService = itemImageService; }

    @Autowired
    public void setSearchService(SearchService searchService) { this.searchService = searchService; }

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setShoppingCartService(ShoppingCartService shoppingCartService) { this.shoppingCartService = shoppingCartService; }

    @GetMapping("adminhome")
    public String adminHome(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "/admin/adminhome";
        }

    @Transactional
    @PostMapping("upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id")Long id, RedirectAttributes redirectAttributes) {
        Item currentItem = itemService.getItemById(id);

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:../admin/adminView/"+id;
            }
                // Get the file and save it somewhere
        String UPLOADED_FOLDER = "C://Users//Admin//Desktop//bikeShop//src//main//resources//static//images//itemImages//";
        Path destPath = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
        try {
            file.transferTo(destPath);
        } catch (IOException e) {
            e.printStackTrace();
            }

        String originalPathStr = "/images/itemImages/"+destPath.getFileName().toString();
        byte[] encodedPath = Base64.getEncoder().encode(originalPathStr.getBytes());

        ItemImage image = new ItemImage(currentItem, encodedPath);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" +
                file.getOriginalFilename() + "' for " + currentItem.getName() );
        itemImageService.saveItemImage(image);
        currentItem.getImages().add(image);
        itemService.saveItem(currentItem);
        return "redirect:../admin/adminView/"+id;
        }

    @GetMapping("adminView/{id}")
    public String adminView(@PathVariable("id")Long id, Model model){
        model.addAttribute("item", itemService.getItemById(id));
        return "admin/adminView";
        }

    @GetMapping("activation/{id}")
    public String activateItem(@PathVariable("id")Long id, RedirectAttributes ra){
        Item currentItem = itemService.getItemById(id);
        if(currentItem.isActive()){
            currentItem.setActive(false);
            ra.addFlashAttribute("activation", currentItem.getName()+" status set to 'Inactive'!");
        }
        else{
            currentItem.setActive(true);
            ra.addFlashAttribute("activation", currentItem.getName()+" status set to 'Active'!");
        }
        itemService.saveItem(currentItem);

        return "redirect:../adminhome";
    }

    @RequestMapping(value = "updateItem", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateItem(@ModelAttribute Item item){
        itemService.saveItem(item);
        return "redirect:../adminView/" + item.getId();
        }

    @Transactional
    @RequestMapping("deleteImage")
    public String deleteImage(@RequestParam("imagePath") String imagePath, @RequestParam("itemId") Long itemId){
        Item item = itemService.getItemById(itemId);

        for(ItemImage itemImage : item.getImages()) {
            if (itemImage.getDecodedImgPath().equals(imagePath)) {
                itemImageService.deleteItemImage(itemImage.getId());
                item.getImages().remove(itemImage);
                break;
            }
        }
        itemService.saveItem(item);
        return "redirect:../admin/adminView/" + itemId;
    }

    @RequestMapping("sort")
    public String getAdminSortedItems(@RequestParam("query")String query, @RequestParam("field") String field, Model model) {
        if(query.equals(null)){
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
                  model.addAttribute("results", searchService.sortAllById());
                break;
                case "priceAsc":
                    model.addAttribute("items", searchService.sortAllByPriceAsc());
                    break;
                case "priceDesc":
                    model.addAttribute("items", searchService.sortAllByPriceDesc());
                    break;
            }
        }
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

        return "admin/adminhome";
    }

    @GetMapping("searchItems")
    public String navSearch(@RequestParam("query") String query, Model model) {
        model.addAttribute("items", searchService.getSearchedItems(query));
        model.addAttribute("query", query);
        return "admin/adminhome";
    }

    @GetMapping("searchUsers")
    public String searchUsers(@RequestParam("userSearch")String userSearch, Model model) {
        model.addAttribute("users", userService.getSearchedUser(userSearch));
        model.addAttribute("userSearch", userSearch);
        return "admin/adminhome";
    }

    @GetMapping("users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin/adminhome";
    }

    @GetMapping("adminUser/{id}")
    public String seeUserDetails(@PathVariable("id") Long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("orders", shoppingCartService.getCompletedCarts(user.getUsername(),true));
        return "admin/adminView";
    }

    @GetMapping("enable/{id}")
    public String enable(@PathVariable("id")Long id, Model model){
        User user = userService.getUser(id);
        if(user.isEnabled()){
            user.setEnabled(false);
            model.addAttribute("activation", user.getUsername() + "'s account disabled.");
        }
        else{
            user.setEnabled(true);
            model.addAttribute("activation", user.getUsername() + "'s account enabled.");
        }
        model.addAttribute("users", userService.getAllUsers());
        userService.saveUser(user);
        return "admin/adminhome";
    }

    @GetMapping("newItem")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "admin/newItem";
    }

    @PostMapping(value = "newItem")
    public String saveNewItem(@ModelAttribute Item item, RedirectAttributes rA){
        itemService.saveItem(item);
        rA.addFlashAttribute("activation", "Item: " + item.getName() + " created succesfully!");
        return "redirect:../adminhome";
    }

    }

