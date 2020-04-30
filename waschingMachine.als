// définition générales
abstract sig Feature {}
one sig Wash , Heat , Delay , Dry extends Feature {}

// définition du feature model
abstract sig FeatureModel {
	feature : some Feature
}

// Wash est obligatoire.
fact {
	all f: FeatureModel | Wash in f. feature
}

abstract sig State {}
one sig Locking , Waiting , Washing , Drying , Unlocking extends State {}

abstract sig Transition {
	source : one State ,
	target : one State
}
one sig StartWash , EndWash, StartPrewash, EndPrewash, StartDry, EndDry extends Transition {}

fact {
	(StartWash.source = Locking and StartWash.target = Waiting)
}
fact {
	(EndWash.source = Drying and EndWash.target = Unlocking)
}
fact {
	(StartPrewash.source = Locking and StartPrewash.target = Waiting)
}

fact {
	(EndPrewash.source = Waiting and EndPrewash.target = Washing)
}
fact {
	(StartDry.source = Washing and StartDry.target = Drying)
}
fact {
	(EndDry.source = Drying and EndDry.target = Unlocking)
}

// définition du domain model
// contrainte des élééments du model
sig DomainModel {
	transition : some Transition ,
	state : some State
}
// 
fact {
	all d: DomainModel | 
		all s : State | 
			some t: Transition |
				(t in d. transition ) and ((s in t. source ) or (s in t. target )) =>
					(s in d. state )
					else (s not in d. state )
}

// définition d'un produit
abstract sig Product {
	domain: one DomainModel ,
	feature : one FeatureModel 
}
one sig P1 , P2 , P3 , P4 , P5 extends Product {}

// conontraintes
fact { 
	all f: FeatureModel | 
		f in Product . feature 
}
fact {
	all d: DomainModel | 
		d in Product .domain
}

// définition d'une ligne de produit.
abstract sig SPL{
product : some Product
}
fact {
	all p: Product |
		p in SPL. product 
}
fact {
all p: Product |
	(Wash in p. feature . feature )=>
		(( StartWash in p.domain. transition ) and (EndWash in p.domain. transition ))
		else (( StartWash not in p.domain. transition ) and (EndWash not in p.domain. transition ))
}
fact {
	all p: Product |
		(Dry in p. feature . feature )=>
		(( StartDry in p.domain. transition ) and (EndDry in p.domain. transition ))
		else (( StartDry not in p.domain. transition ) and (EndDry not in p.domain. transition ))
}
fact {
	all p: Product |
		 (Heat in p. feature . feature ) or ( Delay in p. feature . feature ) =>
		(( StartPrewash in p.domain. transition ) and (EndPrewash in p.domain. transition ))
		else (( StartPrewash not in p.domain. transition ) and (EndPrewash not in p.domain. transition )) 
}

// élimination des cycles.
fact {
	all f1 , f2 : FeatureModel | 
		f1. feature = f2. feature => f1= f2
}
fact {
	all d1 , d2 : DomainModel | 
		d1. transition = d2. transition => d1=d2
}
fact {
	all t1 , t2 : Transition | 
		(t1. source = t2. source ) and (t1. target = t2. target) =>t1=t2
}
fact {
	all p1 , p2 : Product | 
		(p1. feature = p2. feature ) and (p1.domain=p2.domain)=> p1 = p2
}
