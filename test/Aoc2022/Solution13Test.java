package Aoc2022;

import Aoc2022.Solution13.Element;
import org.junit.jupiter.api.Test;

public class Solution13Test {

    @Test
    public void testEmpty() {
        Element firstElement = Solution13.getElementFrom("[]");
        Element secondElement = Solution13.getElementFrom("[]");
        assert 0 == Solution13.compare(firstElement, secondElement);
    }
    @Test
    public void testNumbers() {
        Element firstElement = Solution13.getElementFrom("[1]");
        Element secondElement = Solution13.getElementFrom("[1]");
        assert 0 == Solution13.compare(firstElement, secondElement);
    }

    @Test
    public void testRealCase() {
        Element firstElement = Solution13.getElementFrom("[[],[],[[],10,[[7,0,1,1,10],9,6],[1,[4,9,1],6,[4,6,0]],[0,3,0]],[1,[10,[7,4,3,4],[]],[2,2],7,[[10],5,3]]]");
        Element secondElement = Solution13.getElementFrom("[[8,1,[2,3,3,[6,7,7,2,6]],[[8,10,1]],[9]],[1,7,[7,[3,6],7,7,10]]]");
        assert -1 == Solution13.compare(firstElement, secondElement);
    }
    @Test
    public void testRealCase2() {
        Element firstElement = Solution13.getElementFrom("[[[10,3,[8]],10,1]]");
        Element secondElement = Solution13.getElementFrom("[[[],5,1,[10,[3,8,7],[5],[1,3,6,0],[4,8,10,6]],[[7,7,1],[0,2],8,[2,1,9,8],8]]]");
        assert 1 == Solution13.compare(firstElement, secondElement);
    }

    @Test
    public void testSample() {
        assert -1 == Solution13.compare(Solution13.getElementFrom("[1,1,3,1,1]"), Solution13.getElementFrom("[1,1,5,1,1]"));
        assert -1 == Solution13.compare(Solution13.getElementFrom("[[1],[2,3,4]]"), Solution13.getElementFrom("[[1],4]"));
        assert 1 == Solution13.compare(Solution13.getElementFrom("[9]"), Solution13.getElementFrom("[[8,7,6]]"));
        assert -1 == Solution13.compare(Solution13.getElementFrom("[[4,4],4,4]"), Solution13.getElementFrom("[[4,4],4,4,4]"));
        assert 1 == Solution13.compare(Solution13.getElementFrom("[7,7,7,7]"), Solution13.getElementFrom("[7,7,7]"));
        assert -1 == Solution13.compare(Solution13.getElementFrom("[]"), Solution13.getElementFrom("[3]"));
        assert 1 == Solution13.compare(Solution13.getElementFrom("[[[]]]"), Solution13.getElementFrom("[[]]"));
        assert 1 == Solution13.compare(Solution13.getElementFrom("[1,[2,[3,[4,[5,6,7]]]],8,9]"), Solution13.getElementFrom("[1,[2,[3,[4,[5,6,0]]]],8,9]"));

    }


}