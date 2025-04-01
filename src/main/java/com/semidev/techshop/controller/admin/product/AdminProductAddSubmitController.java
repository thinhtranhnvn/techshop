package com.semidev.techshop.controller.admin.product;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductImageId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.Product;
import com.semidev.techshop.model.entity.ProductImage;
import com.semidev.techshop.model.service.ProductImageService;
import com.semidev.techshop.model.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Controller
public class AdminProductAddSubmitController {
    
    @PostMapping("/admin/product/add-submit")
    public String accept(
        HttpServletRequest request, HttpSession session, Model model,
        @RequestParam(name="brand-id", required=true) int brandId,
        @RequestParam(name="name", required=true) String name,
        @RequestParam(name="image-url", required=true) ArrayList<String> imageURLList,
        @RequestParam(name="price", required=true) float price,
        @RequestParam(name="discount", required=false, defaultValue="0") float discount,
        @RequestParam(name="promotion", required=false, defaultValue="") String promotion,
        @RequestParam(name="description", required=false, defaultValue="") String description,
        @RequestParam(name="specification", required=false, defaultValue="") String specification,
        @RequestParam(name="slug", required=true) String slug
    ) {
        if (session.getAttribute("adminUsername") == null) {
            session.setAttribute("returnURL", request.getRequestURI());
            return "redirect:" + "/admin/login";
        }
        else {
            try {
                session.setAttribute("submittedBrandId", brandId);
                session.setAttribute("submittedName", name);
                session.setAttribute("submittedImageURL", imageURLList);
                session.setAttribute("submittedPrice", price);
                session.setAttribute("submittedDiscount", discount);
                session.setAttribute("submittedPromotion", promotion);
                session.setAttribute("submittedDescription", description);
                session.setAttribute("submittedSpecification", specification);
                session.setAttribute("submittedSlug", slug);
                var matchedSlugProduct = ProductService.selectProductBySlug(slug);
                if (matchedSlugProduct == null) {
                    var productId = ProductService.selectLatestProductId() + 1;
                    var editedDate = LocalDateTime.now();
                    var editedBy = (String) session.getAttribute("adminUsername");
                    var product = Product.createInstance(productId, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                    ProductService.insertIntoProduct(product);
                    for (var imageURL : imageURLList) {
                        if (imageURL.isEmpty()) {
                            /* do nothing */
                        }
                        else {
                            var productImageId = ProductImageService.selectLatestProductImageId() + 1;
                            var productImage = ProductImage.createInstance(productImageId, productId, imageURL);
                            ProductImageService.insertIntoProductImage(productImage);
                        }
                    }
                    session.setAttribute("submittedBrandId", null);
                    session.setAttribute("submittedName", null);
                    session.setAttribute("submittedImageURL", null);
                    session.setAttribute("submittedPrice", null);
                    session.setAttribute("submittedDiscount", null);
                    session.setAttribute("submittedPromotion", null);
                    session.setAttribute("submittedDescription", null);
                    session.setAttribute("submittedSpecification", null);
                    session.setAttribute("submittedSlug", null);
                    session.setAttribute("addError", null);
                    return "redirect:" + "/admin/product";
                }
                else {
                    session.setAttribute("addError", "Slug already existed");
                    return "redirect:" + "/admin/product/add";
                }
            }
            catch (ExceptionInvalidBrandId exc) {
                session.setAttribute("addError", "Invalid brand id");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidImageURL exc) {
                session.setAttribute("addError", "Invalid image URL");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductDescription exc) {
                session.setAttribute("addError", "Invalid product description");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductDiscount exc) {
                session.setAttribute("addError", "Invalid product discount");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductEditedBy exc) {
                session.setAttribute("addError", "Invalid product edited-by");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductEditedDate exc) {
                session.setAttribute("addError", "Invalid product edited-date");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductId exc) {
                session.setAttribute("addError", "Invalid product id");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductImageId exc) {
                session.setAttribute("addError", "Invalid product image id");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductName exc) {
                session.setAttribute("addError", "Invalid product name");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductPrice exc) {
                session.setAttribute("addError", "Invalid product price");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductSlug exc) {
                session.setAttribute("addError", "Invalid product slug");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionInvalidProductSpecification exc) {
                session.setAttribute("addError", "Invalid product specification");
                return "redirect:" + "/admin/product/add";
            }
            catch (ExceptionNullProductPromotion exc) {
                session.setAttribute("addError", "Product promotion cannot be null");
                return "redirect:" + "/admin/product/add";
            }
            catch (SQLException exc) {
                session.setAttribute("addError", "Failed connecting database");
                return "redirect:" + "/admin/product/add";
            }
        }
    }
    
}
