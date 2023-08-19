package com.poly.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.FavouriteDAO;
import com.poly.dao.ProductDAO;
import com.poly.model.Account;
import com.poly.model.Favourite;
import com.poly.model.Product;
import com.poly.service.FavouriteService;



@Service
public class FavouriteServiceImpl implements FavouriteService{

	@Autowired
	FavouriteDAO dao;
	
	@Autowired
	ProductDAO productDao;
	
	@Override
	public Favourite findByUsernameAndProductId(String username, Integer id) {
		return dao.findByUsernameAndProductId(username, id);
	}

	@Override
	public void delete(Favourite favourite) {
		dao.delete(favourite);
	}
	
	@Override
	public Favourite create(Account account, Product item) {
		Favourite existFavourite = findByUsernameAndProductId(account.getUsername(), item.getId());
		if(existFavourite == null) {
			existFavourite = new Favourite();
			existFavourite.setAccount(account);
			existFavourite.setProduct(item);
			existFavourite.setIsLiked(false);
			return dao.save(existFavourite);
		}
		return existFavourite;
	}

	@Override
	public Favourite updateLikeOrUnlike(Account account, Integer id) {
		Product product = productDao.findById(id).get();
		Favourite existFavourite = findByUsernameAndProductId(account.getUsername(), product.getId());
		if(existFavourite.getIsLiked() == false) {
			existFavourite.setIsLiked(true);
		}else {
			existFavourite.setIsLiked(false);
		}
		Favourite updateFavourite = dao.save(existFavourite);
		return updateFavourite;
	}

	@Override
	public List<Object[]> getTotalLikesOfProduct() {
		return dao.getTotalLikesOfProduct();
	}

	@Override
	public List<Object[]> getUserInfoWithProductIsLikedByUsers(Integer id) {
		return dao.getUserInfoWithProductIsLikedByUsers(id);
	}

}
