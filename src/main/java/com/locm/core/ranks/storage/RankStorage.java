package com.locm.core.ranks.storage;

import java.util.ArrayList;
import java.util.List;

import com.locm.core.ranks.obj.Rank;

public class RankStorage {
	
	public static List<Rank> getAllRanks(){
		List<Rank> ranks = new ArrayList<>();
		ranks.add(A);
		ranks.add(B);
		ranks.add(C);
		ranks.add(D);
		ranks.add(E);
		ranks.add(F);
		ranks.add(G);
		ranks.add(H);
		ranks.add(I);
		ranks.add(J);
		ranks.add(K);
		ranks.add(L);
		ranks.add(M);
		ranks.add(N);
		ranks.add(O);
		ranks.add(P);
		ranks.add(Q);
		ranks.add(R);
		ranks.add(S);
		ranks.add(T);
		ranks.add(U);
		ranks.add(V);
		ranks.add(W);
		ranks.add(X);
		ranks.add(Y);
		ranks.add(Z);
		return ranks;
		//todo: add all ranks then finish rankutils.
	}
	
	public static Rank A = new Rank("Gỗ", 1, 150, true, false);
	public static Rank B = new Rank("Sắt", 2, 250, false, false);
	public static Rank C = new Rank("Đồng", 3, 500, false, false);
	public static Rank D = new Rank("Bạc", 4, 1000, false, false);
	public static Rank E = new Rank("Vàng", 5, 2500, false, false);
	public static Rank F = new Rank("Bạch Kim", 6, 5000, false, false);
	public static Rank G = new Rank("Kim Cương", 7, 7500, false, false);
	public static Rank H = new Rank("Tinh Anh", 8, 9000, false, false);
	public static Rank I = new Rank("Cao Thủ", 9, 10000, false, false);
	public static Rank J = new Rank("Quán Quân", 10, 15000, false, false);
	public static Rank K = new Rank("Chí Tôn", 11, 20000, false, false);
	public static Rank L = new Rank("Thách Đấu", 12, 25000, false, false);
	public static Rank M = new Rank("Đỉnh Cao", 13, 50000, false, false);
	public static Rank N = new Rank("Ngôi Sao", 14, 75000, false, false);
	public static Rank O = new Rank("Sao Băng", 15, 100000, false, false);
	public static Rank P = new Rank("Siêu Sao", 16, 500000, false, false);
	public static Rank Q = new Rank("Siêu Đẳng", 17, 1000000, false, false);
	public static Rank R = new Rank("Huyền Thoại", 18, 2000000, false, false);
	public static Rank S = new Rank("Thần Thoại", 19, 5000000, false, false);
	public static Rank T = new Rank("Sử Thi", 20, 10000000, false, false);
	public static Rank U = new Rank("Chiến Thần", 21, 15000000, false, false);
	public static Rank V = new Rank("Siêu Thần", 22, 20000000, false, false);
	public static Rank W = new Rank("Vô Thần - I", 23, 25000000, false, false);
	public static Rank X = new Rank("Vô Thần - II", 24, 30000000, false, false);
	public static Rank Y = new Rank("Vô Địch - I", 25, 40000000, false, false);
	public static Rank Z = new Rank("Vô Địch - II", 26, 50000000, false, true);
}
