# WebService-Syntactic-Matching
WebService-Syntactic-Matching
• ThematchingcanbeSyntacticallyorSemantically.
• Bymatchingwemean,findingthefollowingpairs: • <Region,Area>
• <Price,Cost>
• <Color,Colour>
• Only Consider basic elements (those with built-in types such as int, double, string, date, ...) for matching
• Forthetimebeing,wearelookingonlyatSyntactic matching.
• Then, we extend this to Semantic Matching, where we use ontology.

Syntactic Matching • So how to do syntactic matching ?
• Use Edit-Distance (Given two strings s1 and s2, the edit distance between s1 and s2 is the minimum number of operations required to convert string s1 to s2.)
• http://www.algorithmist.com/index.php/Edit_Distance
• Use WordNet: is a lexical database which is available online, and provides a large repository of English lexical items.
• http://wordnet.princeton.edu/


Matching Degrees:
• No Standard rule for degrees, so for the time being simply assume:
• Exact = 1.0
• Subsumption = 0.8
• Plug-in = 0.6
• Structural = 0.5
• Not matched = 0.0


How to find relationships: isSameAs, subClassOf, hasStructuralRelation
• Of course, using ontology !
• We provide some Java code to play with, but feel free
to extend it or improve it to fit your requirements,
• Use Protégé 4.0 tool to see the ontology.
http://protege.stanford.edu/download/protege/4.0/
• We also provide some SAWSDL annotated web services
• We also unified all ontologies used in those SAWSDL services into one Ontology(SUMO.OWL), so you can just ignore the specified ontology in the SAWSDL file, and use the unified one.
