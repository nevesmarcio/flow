# flow

This library is designed to fight against implementations of big "decision trees" and deep IF-THEN-ELSE structures.
For me personally it is very difficult (close to impossible) to maintain a "flow" state of mind for long periods of time.
The library facilitates the creation of a coding style that approaches decision structures as a graph design exercise:
- The node is the Decision Block
- The edges are the possible values

This coding style has the following advantages:
1) the code produced is very easy to test (decision blocks are easily mockable)
2) logging of which path was activated in runtime makes it really easy to understand what is happening
3) introducing new conditions/ new business logic is an exercise of identifying where in the graph one has to insert another node and its edges.

If this approach saved your life once or twice, feel free to send a token of your appreciation to the author.
[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZKXLMH4U2ZEE4)
