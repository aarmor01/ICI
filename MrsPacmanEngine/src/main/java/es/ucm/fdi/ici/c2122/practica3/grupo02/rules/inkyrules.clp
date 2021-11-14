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
	(slot runAwayType (type NUMBER)) 
	(slot chaseType (type NUMBER)) ) 
	
; -- RULES --
;(defrule INKYrunsAwayMSPACMANclosePPill
;	(MSPACMAN (mindistancePPill ?d)) (test (<= ?d 30)) 
;	=>  
;	(assert (ACTION (id INKYRunsAway) 
;			(info "MSPacMan cerca PPill"))) )

(defrule INKYrunsAway
	(INKY (edible true))
	=>  
	(assert (ACTION (id INKYRunsAway)
			(info "Comestible --> huir")
			(priority 9)
			(runAwayType 1))) )

(defrule INKYrunsAwayMSPACMANclosePPill
	(INKY (edible false)) 
	(MSPACMAN (pacmanDistancePowerPill ?p))
	(CONSTANTS (mindistancePPill ?m))
	(test (<= ?p ?m)) 
	=>  
	(assert (ACTION (id INKYRunsAway)
			(info "MsPacman cerca de PowerPill --> huir")
			(priority 9)
			(runAwayType 1))) )
	
(defrule INKYrunsAwayToBLINKY
	(INKY (edible true))
	(BLINKY (outOfLair true))
	(BLINKY (edible false))
	=>  
	(assert (ACTION (id PINKYRunsAway)
			(info "Comestible & BLINKY No Comestible --> huir a SUE")
			(priority 10)
			(runAwayType 2))) )

(defrule INKYrunsAwayToPINKY
	(INKY (edible true))
	(PINKY (outOfLair true))
	(PINKY (edible false))
	=>  
	(assert (ACTION (id PINKYRunsAway)
			(info "Comestible & PINKY No Comestible --> huir a INKY")
			(priority 10)
			(runAwayType 2))) )

(defrule INKYrunsAwayToSUE
	(INKY (edible true))
	(SUE (outOfLair true))
	(SUE (edible false))
	=>  
	(assert (ACTION (id PINKYRunsAway)
			(info "Comestible & SUE No Comestible --> huir a SUE")
			(priority 10)
			(runAwayType 2))) )

(defrule INKYchases
	(INKY (edible false))
	(INKY (outOfLair true))
	(INKY (distanceToPacman ?d))
	(CONSTANTS (ghostChaseDistance ?g))
	(test (<= ?d ?g))
	=> 
	(assert (ACTION (id INKYChase)
			(info "No comestible --> perseguir")
			(priority 8)
			(chaseType 1))) )
	
(defrule Ambush
	(INKY (edible false))
	(INKY (outOfLair true))
	(INKY (distanceToPacman ?d))
	(CONSTANTS (minPredictionDistance ?g))
	(MSPACMAN (pacmanDistancePowerPill ?p))
	(test (> ?d ?g))
	(test (> ?p 20))
	=> 
	(assert (ACTION (id INKYAmbush)
			(info "Ambush predicts pacman movement")
			(priority 7))) )