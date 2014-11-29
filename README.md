# Performance analysis of Lift's Factory Maker

This project does a very rudamentary performance analysis of Lift's Factory
maker compared to directly accessing a var. The purpose of this demonstration
is to document and investigate the nature of the performance difference between
FactoryMaker and var and to potentially identify the underlying cause behind
significant differences in performance.

## Test Method

The test method for this performance test is as follows:

1. All variables, instances, etc are set up.
2. We warm the var by invoking the method it references 10 times.
3. We invoke the method through the var 1 million times, timing it.
4. We warm the FactoryMaker by invoking the method it references 10 times.
5. We invoke the method through the FactoryMaker 1 million times, timing it.

I don't have an in-depth understanding of what the JVM does to optimize
accesses but I have a vague understanding that it does "something" so the
warming should hopefully account for that.

## Results

With the standard FactoryMaker implementation, the results are as originally
predicted by Diego: invoking the method through the FactoryMaker is
substantially slower. Most test runs yielded something like the following:

```
Results:
Var test time: 58 millis
FactoryMaker test time: 504 millis
```

With Var test time varying wildly (but staying under 60ms) and FactoryMaker
regularly over 500. However with a slight modification to FactoryMaker, that
effectively disables per-session and per-request variance, there was a
significant improvement.

```
Results:
Var test time: 9 millis
FactoryMaker test time: 37 millis
```

Once again, var test time varied a bit between 8-60 and FactoryMaker varied a
bit but stayed under 100. This was acheived by changing line 87 of
Factory.scala to read:

```
override implicit def make: Box[T] = super.make or Full(default.is.apply())
```

As mentioned, this effectively disables the per-session, and per-request
abilities, but there might be some optimization in StackableMaker that would
yield better performance. Without access to some better testing tools at the
moment, this is what I've got.
