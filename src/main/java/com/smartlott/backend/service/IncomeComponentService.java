package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.repositories.IncomeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by greenlucky on 1/12/17.
 * This is class serive of income component
 * @version %I% %G%
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class IncomeComponentService {

    @Autowired
    private IncomeComponentRepository incomeComponentRepository;

    /**
     * Creates a new Income Component, which using save of IncomeComponentRepository
     * extends from CrudRepository to create IncomeComponent.
     * <p>Note: <i>@Transaction</i> make sure transaction complete and update if not @Transaction
     * this update not work because it @Transaction of main IncomeComponentService class set
     * readonly is true </p>
     *
     * @param incomeComponent
     * @return A Income component after create
     * @see IncomeComponentRepository
     */

    @Transactional
    public IncomeComponent create(IncomeComponent incomeComponent) {
        return incomeComponentRepository.save(incomeComponent);
    }

    /**
     * Updates a Income Component, which also using save of IncomeComponentRepository
     * extends from CrudRepository to update IncomeComponent look like above create function
     * but it using hashCode and Equals ID in IncomeComponent Entity decide insert or update
     * <p>Note: <i>@Transaction</i> make sure transaction complete and update if not @Transaction
     * this update not work because it @Transaction of main IncomeComponentService class set
     * readonly is true </p>
     *
     * @param incomeComponent
     * @return A Income component after update
     * @see IncomeComponentRepository
     */
    @Transactional
    public IncomeComponent update(IncomeComponent incomeComponent) {
        return incomeComponentRepository.save(incomeComponent);
    }


    /**
     * Gets a Income component given by id or null if not exist.
     * This function using findOne of IncomeComponentRepository
     * extend from CRUDRepository of JPA api
     * @param id
     * @return A income component or null if not found
     */
    public IncomeComponent getOne(long id) {
        return incomeComponentRepository.findOne(id);
    }

    /**
     * Deletes Income component given by id or do nothing if id not exist
     * This function using delete of IncomeComponentRepository
     * extend from CRUDRepository of JPA api
     * @param id
     */
    @Transactional
    public void delete(long id) {
        incomeComponentRepository.delete(id);
    }


    public List<IncomeComponent> getAll() {
        return incomeComponentRepository.findAll();
    }


    /**
     * Gets all Income component exists all database or null if not exists.
     * This function using  of IncomeComponentRepository
     * Pageable is method of CRUDRepository to pagination page; Pageable has
     * 3 main parameters as: page number return current page, page size is number
     * of record per page and sort. Sort can sort by every filed exist of entity and
     * order by ASC or DESC. We using Pageable to get 3 params from URL page, size
     * and sort example: http://pagination.com?page=1&size=15&sort=id&order=desc,
     * before to assign to findAll we convert pageagble to PageRequest
     * @param pageable
     * @return A page of IncomeComponent or null if not eixst
     * @see Pageable
     * @see PageRequest
     */
    public Page<IncomeComponent> getAll(Pageable pageable){
        return incomeComponentRepository.findAll(pageable);
    }


}
