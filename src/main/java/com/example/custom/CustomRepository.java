package com.example.custom;

import com.example.model.Customer;

public interface CustomRepository {
	void depositUpdate(int id, int amount);
	void withdwarlUpdate(int id, int amount);
	void moneyTransfer(int id,  int amount, int receiverId);
}
