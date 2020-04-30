__root__ : WashingMachine :: ___root__ ;

WashingMachine : Wash :: _WashingMachine ;

Wash : [Delay] [Heat] [Dry] :: _Wash ;

%%

(Delay or Heat) and not (Delay and Heat) ;

