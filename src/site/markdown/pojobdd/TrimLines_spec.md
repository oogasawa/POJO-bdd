
Remove blank lines at the beginning and end of a code snippet.
Leave blank lines in the snippet as they are.

### Example



````
expectation:
ActorSystem system = new ActorSystem("system1");
ActorRef<Root> root = system.actorOf("ROOT", new Root());
root.createChild("child01", new ArrayList<Integer>());
root.createChild("child02", new ArrayList<String>());
root.createChild("child03", new ArrayList<Double>());

ActorRef<ArrayList<Integer>> c1 = system.getActor("child01");
c1.tell((a)->a.add(1));
c1.tell((a)->a.add(2));
c1.tell((a)->a.add(3));
CompletableFuture<String> r1 = c1.ask((a)->{ return a.toString(); });

ActorRef<ArrayList<String>> c2 = system.getActor("child02");
c2.tell((a)->a.add("A"));
c2.tell((a)->a.add("B"));
c2.tell((a)->a.add("C"));
CompletableFuture<String> r2 = c2.ask((a)->{ return a.toString(); });

ActorRef<ArrayList<Double>> c3 = system.getActor("child03");
c3.tell((a)->a.add(1.0));
c3.tell((a)->a.add(2.0));
c3.tell((a)->a.add(3.0));
CompletableFuture<String> r3 = c3.ask((a)->{ return a.toString(); });

CompletableFuture<Void> combined = CompletableFuture.allOf(r1, r2, r3);

String answer = null;
try {
    StringJoiner joiner = new StringJoiner("
");
    combined.get(3, TimeUnit.SECONDS);

    joiner.add(r1.get());
    joiner.add(r2.get());
    joiner.add(r3.get());

    answer = joiner.toString() + "
";
} catch (InterruptedException | ExecutionException | TimeoutException e) {
    e.printStackTrace();
}
system.terminate();

answer:
ActorSystem system = new ActorSystem("system1");
ActorRef<Root> root = system.actorOf("ROOT", new Root());
root.createChild("child01", new ArrayList<Integer>());
root.createChild("child02", new ArrayList<String>());
root.createChild("child03", new ArrayList<Double>());

ActorRef<ArrayList<Integer>> c1 = system.getActor("child01");
c1.tell((a)->a.add(1));
c1.tell((a)->a.add(2));
c1.tell((a)->a.add(3));
CompletableFuture<String> r1 = c1.ask((a)->{ return a.toString(); });

ActorRef<ArrayList<String>> c2 = system.getActor("child02");
c2.tell((a)->a.add("A"));
c2.tell((a)->a.add("B"));
c2.tell((a)->a.add("C"));
CompletableFuture<String> r2 = c2.ask((a)->{ return a.toString(); });

ActorRef<ArrayList<Double>> c3 = system.getActor("child03");
c3.tell((a)->a.add(1.0));
c3.tell((a)->a.add(2.0));
c3.tell((a)->a.add(3.0));
CompletableFuture<String> r3 = c3.ask((a)->{ return a.toString(); });

CompletableFuture<Void> combined = CompletableFuture.allOf(r1, r2, r3);

String answer = null;
try {
    StringJoiner joiner = new StringJoiner("
");
    combined.get(3, TimeUnit.SECONDS);

    joiner.add(r1.get());
    joiner.add(r2.get());
    joiner.add(r3.get());

    answer = joiner.toString() + "
";
} catch (InterruptedException | ExecutionException | TimeoutException e) {
    e.printStackTrace();
}
system.terminate();

diff:

````
