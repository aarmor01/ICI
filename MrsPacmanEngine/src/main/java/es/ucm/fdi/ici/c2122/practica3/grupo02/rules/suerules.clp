; -- FACTS --

; --- GAME INPUT DATA ---	
(deftemplate BLINKY
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate INKY
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate PINKY
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate SUE
	(slot edible (type SYMBOL))
    (slot edTimeLeft (type NUMBER))
    (slot outOfLair (type SYMBOL))
	(slot position (type NUMBER))
	(slot distanceToPacman (type NUMBER))
	(slot anotherGhostInPath (type SYMBOL))
	(slot chaseCount (type NUMBER)) )

(deftemplate MSPACMAN 
    (slot pacmanDistancePowerPill (type NUMBER))
	(slot nextPillPacManBySeer (type NUMBER)) )
	
(deftemplate CONSTANTS
	(slot ghostChaseDistance (type NUMBER))
	(slot mindistancePPill (type NUMBER))
	(slot minPredictionDistance (type NUMBER))
	(slot minIntersectionsBeforeChange (type NUMBER)) )

; --- ACTION ---
(deftemplate ACTION
	(slot id)
	(slot info (default ""))
	(slot priority (type NUMBER))
	(slot runAwayType (type NUMBER)) )   
	
; -- RULES --
;(defrule SUErunsAwayMSPACMANclosePPill
;	(MSPACMAN (mindistancePPill ?d)) (test (<= ?d 30)) 
;	=>  
;	(assert (ACTION (id SUERunsAway) 
;			(info "MSPacMan cerca PPill"))) )

(defrule SUErunsAway
	(SUE (edible true)) 
	=>  
	(assert (ACTION (id SUERunsAway)
			(info "Comestible --> huir")
			(priority 9)
			(runAwayType 1))) )

(defrule SUErunsAwayMSPACMANclosePPill
	(SUE (edible false)) 
	(MSPACMAN (pacmanDistancePowerPill ?p))
	(CONSTANTS (mindistancePPill ?m))
	(test (<= ?p ?m)) 
	=>  
	(assert (ACTION (id SUERunsAway)
			(info "MsPacman cerca de PowerPill --> huir")
			(priority 9)
			(runAwayType 1))) )
	
(defrule SUErunsAwayToBLINKY
	(SUE (edible true))
	(BLINKY (outOfLair true))
	(BLINKY (edible false))
	=>  
	(assert (ACTION (id PINKYRunsAway)
			(info "Comestible & BLINKY No Comestible --> huir a SUE")
			(priority 10)
			(runAwayType 2))) )

(defrule SUErunsAwayToPINKY
	(SUE (edible true))
	(PINKY (outOfLair true))
	(PINKY (edible false))
	=>  
	(assert (ACTION (id PINKYRunsAway)
			(info "Comestible & PINKY No Comestible --> huir a INKY")
			(priority 10)
			(runAwayType 2))) )

(defrule SUErunsAwayToINKY
	(SUE (edible true))
	(INKY (outOfLair true))
	(INKY (edible false))
	=>  
	(assert (ACTION (id PINKYRunsAway)
			(info "Comestible & INKY No Comestible --> huir a SUE")
			(priority 10)
			(runAwayType 2))) )

(defrule SUEchases
	(SUE (edible false))
	(SUE (outOfLair true))
	(SUE (distanceToPacman ?d))
	(CONSTANTS (ghostChaseDistance ?g))
	(test (<= ?d ?g))
	=> 
	(assert (ACTION (id SUEChase)
			(info "No comestible --> perseguir")
			(priority 7)
			(chaseType 1))) )

(defrule SUEchasesAlternative
	(SUE (edible false))
	(SUE (outOfLair true))
	(SUE (distanceToPacman ?d))
	(SUE (chaseCount ?c))
	(CONSTANTS (ghostChaseDistance ?g))
	(test (<= ?d ?g))
	(test (> ?c 2))
	=> 
	(assert (ACTION (id SUEChase)
			(info "No comestible --> perseguir distinto camino")
			(priority 8)
			(chaseType 2))) )		
			
(defrule Agressive
	(SUE (edible false))
	(SUE (outOfLair true))
	(SUE (distanceToPacman ?d))
	(MSPACMAN (pacmanDistancePowerPill ?p))
	(CONSTANTS (minPredictionDistance ?g))
	(test (> ?d ?g))
	(test (> ?p 20))
	=> 
	(assert (ACTION (id SUEAgressive)
			(info "Agressive chases pacman")
			(priority 6))) )