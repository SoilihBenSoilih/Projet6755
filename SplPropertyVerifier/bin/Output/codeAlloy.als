//Definition generale

abstract sig Feature{}

one sig Wash,Delay,Heat,Dry extends Feature{}

//Définition du feature model

abstract sig FeatureModel {
   feature : some Feature
}

// Wash est obligatoire

fact {
   all f: FeatureModel | Wash in f. feature
}



abstract sig State{}

one sig Locking,Waiting,Washing,Drying,Unlocking extends State{}

abstract sig Transition {
	source : one State,
	target : one State
}

one sig startPrewash,endPrewash,startDrying,endDrying,startWash,endWash extends Transition{}

fact {
	(startPrewash.source = Locking and startPrewash.target = Waiting)
}

fact {
	(endPrewash.source = Waiting and endPrewash.target = Washing)
}

fact {
	(startDrying.source = Washing and startDrying.target = Drying)
}

fact {
	(endDrying.source = Drying and endDrying.target = Unlocking)
}

fact {
	(startWash.source = Locking and startWash.target = Washing)
}

fact {
	(endWash.source = Washing and endWash.target = Unlocking)
}

// définition du domain model
// contrainte des éléments du model
sig DomainModel {
	transition : some Transition ,
	state : some State
}

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

one sig P1,P2,P3,P4,P5 extends Product{}

// définition d'une ligne de produit.

abstract sig SPL{
product : some Product
}

fact {
	all p: Product |
		p in SPL.product 
}

//considération du mapping 

fact {
	all p: Product |
		 (Wash in p.feature.feature ) =>
		(( startWash in p.domain.transition ) and (endWash in p.domain.transition ))
		else (( startWash not in p.domain.transition ) and (endWash not in p.domain.transition )) 
}

fact {
	all p: Product |
		 (Heat in p.feature.feature ) =>
		(( startPrewash in p.domain.transition ) and (endPrewash in p.domain.transition ))
		else (( startPrewash not in p.domain.transition ) and (endPrewash not in p.domain.transition )) 
}

fact {
	all p: Product |
		 (Delay in p.feature.feature ) =>
		(( startPrewash in p.domain.transition ) and (endPrewash in p.domain.transition ))
		else (( startPrewash not in p.domain.transition ) and (endPrewash not in p.domain.transition )) 
}

fact {
	all p: Product |
		 (Dry in p.feature.feature ) =>
		(( startDrying in p.domain.transition ) and (endDrying in p.domain.transition ))
		else (( startDrying not in p.domain.transition ) and (endDrying not in p.domain.transition )) 
}

assert AllStatesSource { all s : State | some t : Transition | t.source = s }
check AllStatesSource for 20
