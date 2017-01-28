package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Category;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.service.CategoryService;
import com.smartlott.backend.service.I18NService;
import com.smartlott.enums.MessageType;
import com.smartlott.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 1/27/17.
 */
@RestController
@RequestMapping(CategoryRestController.CATEGORY_URL)
public class CategoryRestController {

    public static final String CATEGORY_URL = "/api/category";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS;

    /**
     * Creates new category given by category.
     *
     * @param category
     * @param locale
     * @return A successful message or error message if occurs error
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createCategory(@RequestBody Category category, Locale locale){
        messageDTOS = new ArrayList<>();

        category = categoryService.create(category);
        if(category.getId() == 0){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.category.error.add.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.category.success.add.text", category.getTitle(),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    /**
     * Updates category given by categoryId and category.
     * If categoryId not found will be return error message.
     * Otherwise return successful message
     *
     * @param categoryId
     * @param category
     * @param locale
     * @return A successful message or error message if occurs error
     */
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCategory(@PathVariable int categoryId, @RequestBody Category category, Locale locale){
        messageDTOS = new ArrayList<>();

        if(!categoryService.exist(categoryId)){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.category.error.category.id.not.found.text", String.valueOf(categoryId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        categoryService.update(category);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.category.success.update.text", String.valueOf(categoryId),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    /**
     * Deletes category given by category id.
     * If category id is not found which returns error message.
     * Otherwise which will return successful message.
     *
     * @param categoryId
     * @param locale
     * @return A successful message or error if occurs error
     */
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCategory(@PathVariable int categoryId, Locale locale){
        messageDTOS = new ArrayList<>();

        if(!categoryService.exist(categoryId)){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.category.error.category.id.not.found.text", String.valueOf(categoryId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        categoryService.delete(categoryId);
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.post.category.success.delete.text", String.valueOf(categoryId),locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    /**
     * Retrieves a page of category or null if not exist
     * @param pageable
     * @param locale
     * @return A page of category or null if not exist
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCategory(Pageable pageable, Locale locale){
        messageDTOS = new ArrayList<>();

        PageRequest pageRequest = PageRequestUtils.createPageRequest(pageable);

        Page<Category> categoryPage = categoryService.getAll(pageable);

        if(categoryPage.getTotalPages() == 0){
            messageDTOS.add(new MessageDTO(MessageType.WARNING,
                    i18NService.getMessage("admin.post.category.error.category.not.found.text" ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(categoryPage, HttpStatus.OK);
    }

    /**
     * Retrieves a category given category id or message error if not found
     * @param categoryId
     * @param locale
     * @return A category or message error if not found
     */
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategory(@PathVariable int categoryId, Locale locale){
        messageDTOS = new ArrayList<>();

        Category category = categoryService.getOne(categoryId);

        if(category == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.post.category.error.category.id.not.found.text", String.valueOf(categoryId) ,locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(category, HttpStatus.OK);
    }


}
