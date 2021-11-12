;---------FACTS ASSERTED BY GAME INPUT-------------------------------
(deftemplate BLINKY
	(slot edible (type SYMBOL)))
	
(deftemplate INKY
	(slot edible (type SYMBOL)))
	
(deftemplate PINKY
	(slot edible (type SYMBOL)))

(deftemplate SUE
	(slot edible (type SYMBOL)))
	
(deftemplate MSPACMAN
	(slot existenPillsAbajo (type SYMBOL))
	(slot caminoBloqueado (type SYMBOL))
	)
    
;--------DEFINITION OF THE ACTION FACT---------------------------------
(deftemplate ACTION
	(slot id) (slot info (default "")) (slot priority (type NUMBER) )) 
   
;----------------RULES-------------------------------------------------
(defrule CleanBottomPill
	(MSPACMAN (existenPillsAbajo true))
	=>  
	(assert (ACTION (id PCcleansBottom) (info "MSPacMan limpia fondo") (priority 1) )))	
	
(defrule PathBlocked
	(MSPACMAN (existenPillsAbajo true))
	=>  
	(assert (ACTION (id RunawayFromClosestGhost) (info "camino del MSPacMan bloqueado --> Huir / busca nuevo camino") (priority 1) ) ) )	
	
	