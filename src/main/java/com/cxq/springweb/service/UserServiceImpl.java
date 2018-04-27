package com.cxq.springweb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cxq.springweb.bean.JsonResult;
import com.cxq.springweb.bean.PageQueryParam;
import com.cxq.springweb.bean.User;
import com.cxq.springweb.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public JsonResult getUserListPage(PageQueryParam pageQueryParam, final User user) {
		Pageable pageable = new PageRequest(pageQueryParam.getPageNumber() - 1, pageQueryParam.getPageSize(),
				new Sort(pageQueryParam.getSortOrder().equals("ASC") ? Direction.ASC : Direction.DESC, pageQueryParam.getSortName()));
		
		Page<User> userPage = userRepository.findAll(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
                if (null != user.getName() && user.getName() != "") {
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + user.getName() + "%"));
                }
                if (null != user.getSex() && user.getSex() != "") {
                    predicates.add(cb.equal(root.get("sex"), user.getSex()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                //return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
			
		},pageable);

		JsonResult jsonResult = new JsonResult();
		jsonResult.setRows(userPage.getContent());
		jsonResult.setTotal(userPage.getTotalElements());
		return jsonResult;
	}

	@Override
	public User createUser(User user) {
		if (user.getId() == "") {
			user.setId(null);
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> getUserList() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(String ids) {
		String[] userIds = ids.split(",");
		userRepository.deleteUsers(userIds);
	}

	@Override
	public User getUserById(String id) {
		return userRepository.findOne(id);
	}

}
