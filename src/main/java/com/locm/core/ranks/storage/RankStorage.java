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
	
	public static Rank A = new Rank("Gỗ", 1, 450, true, false);
	public static Rank B = new Rank("Sắt", 2, 750, false, false);
	public static Rank C = new Rank("Đồng", 3, 1500, false, false);
	public static Rank D = new Rank("Bạc", 4, 3000, false, false);
	public static Rank E = new Rank("Vàng", 5, 7500, false, false);
	public static Rank F = new Rank("Bạch Kim", 6, 15000, false, false);
	public static Rank G = new Rank("Kim Cương", 7, 22500, false, false);
	public static Rank H = new Rank("Tinh Anh", 8, 27000, false, false);
	public static Rank I = new Rank("Cao Thủ", 9, 30000, false, false);
	public static Rank J = new Rank("Quán Quân", 10, 45000, false, false);
	public static Rank K = new Rank("Chí Tôn", 11, 80000, false, false);
	public static Rank L = new Rank("Thách Đấu", 12, 100000, false, false);
	public static Rank M = new Rank("Đỉnh Cao", 13, 200000, false, false);
	public static Rank N = new Rank("Ngôi Sao", 14, 300000, false, false);
	public static Rank O = new Rank("Sao Băng", 15, 400000, false, false);
	public static Rank P = new Rank("Siêu Sao", 16, 2000000, false, false);
	public static Rank Q = new Rank("Siêu Đẳng", 17, 4000000, false, false);
	public static Rank R = new Rank("Huyền Thoại", 18, 8000000, false, false);
	public static Rank S = new Rank("Thần Thoại", 19, 20000000, false, false);
	public static Rank T = new Rank("Sử Thi", 20, 40000000, false, false);
	public static Rank U = new Rank("Chiến Thần", 21, 75000000, false, false);
	public static Rank V = new Rank("Siêu Thần", 22, 100000000, false, false);
	public static Rank W = new Rank("Vô Thần - I", 23, 125000000, false, false);
	public static Rank X = new Rank("Vô Thần - II", 24, 150000000, false, false);
	public static Rank Y = new Rank("Vô Địch - I", 25, 200000000, false, false);
	public static Rank Z = new Rank("Vô Địch - II", 26, 250000000, false, true);
}
