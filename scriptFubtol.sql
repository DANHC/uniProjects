CREATE TABLE torneo(
    id              NUMBER NOT NULL,
    nombreTorneo    VARCHAR2(80) NOT NULL,
    finalizado      NUMBER DEFAULT 0,
    programacionFinalizada NUMBER DEFAULT 0,
    CONSTRAINT torneo_pk PRIMARY KEY(id)
);

create or replace view vw_torneo AS
select * from torneo where ROWNUM <= 1;

select * from vw_torneo;

update torneo set programacionFinalizada = 0;

CREATE SEQUENCE torneo_seq;

CREATE OR REPLACE TRIGGER trg_torneo_id
    BEFORE INSERT ON torneo
    FOR EACH ROW
BEGIN
    SELECT torneo_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/

CREATE TABLE equipo(
    id              NUMBER NOT NULL,
    nombre          VARCHAR2(70) NOT NULL,
    rutaLogo        VARCHAR2(200) NOT NULL,
    idEntrenador    NUMBER NOT NULL,
    presidente      VARCHAR2(100) NOT NULL,
    estadio         VARCHAR2(100) NOT NULL,
    ubicacion       VARCHAR2(100) NOT NULL,
    detalles        CLOB NOT NULL,
    descalificado   NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT equipo_pk PRIMARY KEY(id)
);

CREATE OR REPLACE VIEW vw_equipo AS 
SELECT E.id, E.nombre, E.rutaLogo, E.idEntrenador, E.presidente, E.estadio, E.ubicacion, E.detalles, E.descalificado, EN.nombres AS entrenador
FROM equipo E
JOIN entrenador EN ON E.idEntrenador = EN.id;

select * from vw_equipo;

CREATE SEQUENCE equipo_seq;


CREATE OR REPLACE TRIGGER trg_equipo_id
    BEFORE INSERT ON equipo
    FOR EACH ROW
BEGIN
    SELECT equipo_seq.nextval
    INTO :new.id
    FROM DUAL;
END; 
/

CREATE TABLE jugador(
    id              NUMBER NOT NULL,
    idEquipo        NUMBER NOT NULL,
    nombre          VARCHAR2(70) NOT NULL,
    apellido        VARCHAR2(70) NOT NULL,
    edad            NUMBER NOT NULL,
    estatura        NUMBER(5,2) NOT NULL,
    nacionalidad    VARCHAR2(50) NOT NULL,
    numeroJugador   NUMBER NOT NULL,
    posicion        VARCHAR2(60) NOT NULL,
    rutaFoto        VARCHAR2(100) NOT NULL,
    descalificado   NUMBER(1) DEFAULT 0,
    CONSTRAINT jugador_pk PRIMARY KEY(id)
);




CREATE OR REPLACE VIEW vw_jugador AS
SELECT J.id AS idJugador, J.idEquipo, J.nombre, J.apellido, J.edad, J.estatura, J.nacionalidad, J.numeroJugador, J.posicion, J.rutaFoto AS foto, J.descalificado,
E.nombre AS equipo FROM jugador J
JOIN equipo E ON J.idEquipo = E.id;

SELECT * FROM vw_jugador;

SELECT COUNT(*) AS numeroJugadores FROM jugador WHERE idEquipo = 3;

CREATE SEQUENCE jugador_seq;

CREATE OR REPLACE TRIGGER trg_jugador_id
    BEFORE INSERT ON jugador
    FOR EACH ROW
BEGIN
    SELECT jugador_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/

CREATE TABLE arbitro(
    id              NUMBER NOT NULL,
    nombres         VARCHAR2(50) NOT NULL,
    apellidos       VARCHAR2(50) NOT NULL,
    edad            NUMBER NOT NULL,
    sexo            VARCHAR2(30) NOT NULL,
    nacionalidad    VARCHAR2(50) NOT NULL,
    eliminado       NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT arbitro_pk PRIMARY KEY(id)
);

CREATE SEQUENCE arbitro_seq;

CREATE OR REPLACE TRIGGER trg_arbitro_id
    BEFORE INSERT ON arbitro
    FOR EACH ROW
BEGIN
    SELECT arbitro_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/


CREATE TABLE programacionPartido(
    id          NUMBER NOT NULL,
    idTorneo    NUMBER NOT NULL,
    idLocal     NUMBER NOT NULL,
    idVisitante NUMBER NOT NULL,
    idArbitro   NUMBER NOT NULL,
    fecha       VARCHAR2(50) NOT NULL,
    finalizado  NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT programacionPartido_pk PRIMARY KEY(id)
);


select * from programacionPartido where finalizado = 0 order by fecha asc;

select * from vw_progPartido;

select * from equipo;

CREATE OR REPLACE VIEW vw_progPartidoTodos AS
select P.id, P.finalizado, T.id AS torneoId, T.nombreTorneo, E.id as localId, E.nombre as localNombre, E.rutaLogo as localLogo, EQ.id as visitanteId, EQ.nombre as visitanteNombre,
EQ.rutaLogo as visitanteLogo, AR.id as arbitroId, AR.nombres as arbitroNombre, P.fecha FROM
programacionPartido P
JOIN Torneo T ON T.id = P.idTorneo
JOIN Equipo E ON E.id = P.idLocal
JOIN Equipo EQ ON EQ.id = P.idVisitante
JOIN Arbitro AR ON AR.id = P.idArbitro
order by P.fecha asc;

select * from vw_progPartidoTodos;

CREATE OR REPLACE VIEW vw_progPartido AS
select P.id, P.finalizado, T.id AS torneoId, T.nombreTorneo, E.id as localId, E.nombre as localNombre, E.rutaLogo as localLogo, EQ.id as visitanteId, EQ.nombre as visitanteNombre,
EQ.rutaLogo as visitanteLogo, AR.id as arbitroId, AR.nombres as arbitroNombre, P.fecha FROM
programacionPartido P
JOIN Torneo T ON T.id = P.idTorneo
JOIN Equipo E ON E.id = P.idLocal
JOIN Equipo EQ ON EQ.id = P.idVisitante
JOIN Arbitro AR ON AR.id = P.idArbitro
where P.finalizado = 0
order by P.fecha asc;

select count(*) AS numeroJugadores from jugador where idEquipo = 3;

SELECT * FROM vw_equipo;

select * from vw_progPartido where ROWNUM = 1;
select * from arbitro;
select * from torneo;
select * from vw_torneo;

SELECT * FROM V$RESOURCE_LIMIT WHERE resource_name IN ('processes','sessions');

update torneo set programacionFinalizada = 0;

SELECT * FROM vw_jugador;

select * from programacionPartido;

update programacionPartido set finalizado = 0 where id = 85;

SELECT * FROM vw_progPartido WHERE ROWNUM = 1;

CREATE SEQUENCE programacionPartido_seq;

CREATE OR REPLACE TRIGGER trg_programacionPartido_id
    BEFORE INSERT ON programacionPartido
    FOR EACH ROW
BEGIN
    SELECT programacionPartido_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/


CREATE TABLE entrenador(
    id              NUMBER NOT NULL,
    idUsuario       NUMBER NOT NUll,
    nombres         VARCHAR2(50) NOT NULL,
    apellidos       VARCHAR2(50) NOT NULL,
    edad            NUMBER NOT NULL,
    sexo            VARCHAR2(30) NOT NULL,
    nacionalidad    VARCHAR2(50) NOT NULL,
    eliminado       NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT entrenador_pk PRIMARY KEY(id)
);

CREATE OR REPLACE VIEW vw_entrenador AS
SELECT E.id AS entrenadorId, U.id AS usuarioId, U.contra, U.nombreUsuario, U.tipoUsuario, E.nombres, E.apellidos, E.edad, E.sexo, E.nacionalidad, E.eliminado
FROM entrenador E
JOIN usuario U ON E.idUsuario = U.id;

select * from vw_entrenador;

CREATE SEQUENCE entrenador_seq;

CREATE OR REPLACE TRIGGER trg_entrenador_id
    BEFORE INSERT ON entrenador
    FOR EACH ROW
BEGIN
    SELECT entrenador_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/

CREATE TABLE partido(
    id                      NUMBER NOT NULL,
    idProgramacionPartido   NUMBER NOT NULL,
    totalGoles              NUMBER DEFAULT 0,
    totalFaltas             NUMBER DEFAULT 0,
    totalCorner             NUMBER DEFAULT 0,
    totalPenalti            NUMBER DEFAULT 0,
    golesLocal              NUMBER DEFAULT 0,
    golesVisitante          NUMBER DEFAULT 0,
    CONSTRAINT partido_pk PRIMARY KEY(id)
);

select * from partido;

ALTER TABLE partido ADD golesLocal NUMBER DEFAULT 0;
ALTER TABLE partido ADD golesVisitante NUMBER DEFAULT 0;


CREATE VIEW vw_partido_id AS
SELECT MAX(id) AS IDPART FROM partido;

select * from partido;
select * from vw_partido_id;

CREATE SEQUENCE partido_seq;

delete from partido;

CREATE OR REPLACE TRIGGER trg_partido_id
    BEFORE INSERT ON partido
    FOR EACH ROW
BEGIN
    SELECT partido_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/

select max(id) as id from partido;


CREATE TABLE gol(
    id          NUMBER NOT NULL,
    idPartido   NUMBER NOT NULL,
    idJugador   NUMBER NOT NULL,
    CONSTRAINT pk_gol PRIMARY KEY(id)
);



CREATE OR REPLACE VIEW vw_goleadores AS
select G.idJugador, count(G.idJugador) as goles FROM GOL G 
GROUP BY G.idJugador
ORDER BY goles DESC;

SELECT * FROM vw_goleadores WHERE ROWNUM = 1;

select * from gol;
delete from gol;



CREATE SEQUENCE gol_seq;

CREATE OR REPLACE TRIGGER trg_gol_id
    BEFORE INSERT ON gol
    FOR EACH ROW
BEGIN
    SELECT gol_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/


CREATE TABLE faltasPartido(
    id              NUMBER NOT NULL,
    idPartido       NUMBER NOT NULL,
    idJugador       NUMBER NOT NULL,
    tipoFalta       VARCHAR2(40) NOT NULL,
    CONSTRAINT pk_faltasPartido PRIMARY KEY(id)
);

select * from faltasPartido;
delete from faltasPartido;

CREATE SEQUENCE faltasPartido_seq;

CREATE OR REPLACE TRIGGER trg_faltasPartido_id
    BEFORE INSERT ON faltasPartido
    FOR EACH ROW
BEGIN
    SELECT faltasPartido_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/

CREATE TABLE estadisticasEquipo(
    id                  NUMBER NOT NULL,
    idEquipo            NUMBER NOT NULL,
    partidosEmpatados   NUMBER DEFAULT 0,
    partidosGanados     NUMBER DEFAULT 0,
    partidosPerdidos    NUMBER DEFAULT 0,
    partidosJugados     NUMBER DEFAULT 0,
    golesAFavor         NUMBER DEFAULT 0,
    golesEnContra       NUMBER DEFAULT 0,
    puntos              NUMBER DEFAULT 0,
    CONSTRAINT estadisticasPartido_pk PRIMARY KEY(id)
);

CREATE OR REPLACE VIEW vw_equiposEstadisticas AS
SELECT ROW_NUMBER() OVER (ORDER BY 'ES.puntos', 'DESC') AS POS, E.id, ES.id AS esId, E.nombre, E.rutaLogo, ES.partidosJugados AS PJUG, ES.partidosGanados AS PG, ES.partidosEmpatados AS EM, ES.partidosPerdidos AS PER,
ES.golesAFavor AS GlFav, ES.golesEnContra AS GlCon, ES.puntos as PNTS
FROM estadisticasEquipo ES
JOIN equipo E ON ES.idEquipo = E.id;

select * from vw_equiposEstadisticas;

select * from estadisticasEquipo;
select * from equipo;

CREATE SEQUENCE estadisticasEquipo_seq;

CREATE OR REPLACE TRIGGER trg_estadisticasEquipo_id
    BEFORE INSERT ON estadisticasEquipo
    FOR EACH ROW
BEGIN
    SELECT estadisticasEquipo_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/


CREATE TABLE usuario(
    id              NUMBER NOT NULL,
    nombreUsuario   VARCHAR2(100) NOT NULL,
    contra          VARCHAR2(150) NOT NULL,
    tipoUsuario     VARCHAR2(50) NOT NULL,
    eliminado       NUMBER DEFAULT 0 NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY(id),
    CONSTRAINT unique_nombreUsuario UNIQUE(nombreUsuario)
);


INSERT INTO usuario(nombreUsuario, contra, tipoUsuario)
VALUES('admin', 'admin123', 'Administrador');

select * from usuario;

CREATE SEQUENCE usuario_seq;

CREATE OR REPLACE TRIGGER trg_usuario_id
    BEFORE INSERT ON usuario
    FOR EACH ROW
BEGIN
    SELECT usuario_seq.nextval
    INTO :new.id
    FROM DUAL;
END;
/

SELECT * FROM usuario WHERE nombreUsuario = 'admin' AND contra = 'admin123';

ALTER TABLE jugador ADD CONSTRAINT fk_jugador_equipo FOREIGN KEY(idEquipo) REFERENCES equipo(id);
ALTER TABLE equipo ADD CONSTRAINT fk_equipo_entrenador FOREIGN KEY(idEntrenador) REFERENCES entrenador(id);
ALTER TABLE programacionPartido ADD CONSTRAINT fk_programacionPartido_torneo FOREIGN KEY(idTorneo) REFERENCES torneo(id);
ALTER TABLE programacionPartido ADD CONSTRAINT fk_programacionPartido_local FOREIGN KEY(idLocal) REFERENCES equipo(id);
ALTER TABLE programacionPartido ADD CONSTRAINT fk_programacionPartido_visit FOREIGN KEY(idVisitante) REFERENCES equipo(id);
ALTER TABLE programacionPartido ADD CONSTRAINT fk_programacionPartido_arbit FOREIGN KEY(idArbitro) REFERENCES arbitro(id);
ALTER TABLE entrenador ADD CONSTRAINT fk_entrenador_user FOREIGN KEY(idUsuario) REFERENCES usuario(id);
ALTER TABLE partido ADD CONSTRAINT fk_partido_progPart FOREIGN KEY(idProgramacionPartido) REFERENCES programacionPartido(id);
ALTER TABLE gol ADD CONSTRAINT fk_gol_partido FOREIGN KEY(idPartido) REFERENCES partido(id);
ALTER TABLE gol ADD CONSTRAINT fk_gol_jugador FOREIGN KEY(idJugador) REFERENCES jugador(id);
ALTER TABLE faltasPartido ADD CONSTRAINT fk_faltasPart_part FOREIGN KEY(idPartido) REFERENCES partido(id);
ALTER TABLE faltasPartido ADD CONSTRAINT fk_faltasPart_jug FOREIGN KEY(idJugador) REFERENCES jugador(id);
ALTER TABLE estadisticasEquipo ADD CONSTRAINT fk_estadistics_equipo FOREIGN KEY(idEquipo) REFERENCES equipo(id);














