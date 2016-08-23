package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Category;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.models.UserInfo;
import com.luxoft.wheretogo.utils.ImageUtils;
import com.luxoft.wheretogo.utils.PropertiesUtils;
import com.luxoft.wheretogo.services.CategoriesService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UsersRepositoryImpl extends AbstractRepository<User> implements UsersRepository {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private CategoriesService categoriesService;

	public UsersRepositoryImpl() {
		super(User.class);
	}

	@Override
	public void add(UserInfo user) {
		User u = new User();
		u.setEmail(user.getEmail());
		u.setPassword(encoder.encode(user.getPassword()));
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setDescription(user.getDescription());
		u.setPhoneNumber(user.getPhoneNumber());
        u.setBirthday(user.getBirthday());
		u.setActive(true);
		u.setPicture(ImageUtils.generatePicturePath(user.getPicture(), PropertiesUtils.getProp("users.images.path")));
		u.setUser_categories(user.getInterestingCategories());
		Set<Category> userCategories = new HashSet<Category>();
		if(user.getInterestingCategories().size() < 1){
			userCategories.addAll(categoriesService.findAll());
			u.setUser_categories(userCategories);
		}
		if (findByEmail(u.getEmail()) == null) {
			super.add(u);
		}
		super.update(u);
	}

	@Override
	public List<User> findAll() {
		return super.findAll(null);
	}

	@Override
	public User findById(long userId) {
		return super.findByProperty("id", userId);
	}

	@Override
	public User findByEmail(String userLogin) {
		return super.findByProperty("email", userLogin);
	}

	@Override
	public void update(User user) {
		super.update(user);
	}

	// XXX
	@Override
	public List<User> getNotParticipants(long groupId) {
		Criterion criterion = Restrictions.
				sqlRestriction("from User u where u.id not in ( select g.groupParticipants from Group where g.id=?)",
						groupId, LongType.INSTANCE);
		return super.executeCriterion(criterion);
	}

}
