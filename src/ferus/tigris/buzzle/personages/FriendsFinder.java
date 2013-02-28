package ferus.tigris.buzzle.personages;

import java.util.ArrayList;
import java.util.List;

import ferus.tigris.buzzle.Matrix;

import android.graphics.Point;

public class FriendsFinder {
	List<AbstractBehavior> friends = new ArrayList<AbstractBehavior>();

	public FriendsFinder() {
		super();
	}
	
	public List<AbstractBehavior> getFriends(Matrix matrix, Point position) {
		AbstractBehavior b = matrix.getMark(position.x, position.y);
		friends.add(b);
		for(int i = -1; i <= 1; i++) {
			for(int k = -1; k <= 1; k++) {
				int x = position.x + i;
				int y = position.y + k;
				if(isInvalidFriend(i, k))continue;
				
				saveRecursiveGetFriends(matrix, b, x, y);
			}
		}
		return friends;
	}

	protected void saveRecursiveGetFriends(Matrix matrix, AbstractBehavior b, int x, int y) {
		try {
			recursiveGetFriends(matrix, b, x, y);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
		}
	}

	protected void recursiveGetFriends(Matrix matrix, AbstractBehavior b, int x, int y) {
		AbstractBehavior friend = matrix.getMark(x, y);
		if(isMyFriend(b, friend)) {
			getFriends(matrix, new Point(x, y));
		}
	}

	private boolean isInvalidFriend(int i, int k) {
		return ((i == 0)&&(k == 0))||((i != 0)&&(k != 0));
	}

	private boolean isMyFriend(AbstractBehavior b, AbstractBehavior friend) {
		return friend.getType().equalsIgnoreCase(b.getType())&&isNotInMyFriends(friend)&&friend.isAvailable();
	}

	public boolean isNotInMyFriends(AbstractBehavior friend) {
		return !friends.contains(friend);
	}

}