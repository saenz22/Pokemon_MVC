# 🎮 Pokémon Battle Simulator MVC

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![GitHub](https://img.shields.io/badge/Git%20&%20GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Kanban](https://img.shields.io/badge/Kanban-0052CC?style=for-the-badge&logo=trello&logoColor=white)
![NetBeans](https://img.shields.io/badge/GUI%20con%20NetBeans-1B6AC6?style=for-the-badge&logo=apachenetbeanside&logoColor=white)
![UML](https://img.shields.io/badge/UML-D63200?style=for-the-badge&logo=simpleicons&logoColor=white)
![POO](https://img.shields.io/badge/POO-4B0082?style=for-the-badge&logo=abstract&logoColor=white)
![Eventos](https://img.shields.io/badge/Programaci%C3%B3n%20por%20Eventos-FF69B4?style=for-the-badge&logo=eventbrite&logoColor=white)
![MVC](https://img.shields.io/badge/Arquitectura-MVC-0066A1?style=for-the-badge&logo=codeberg&logoColor=white)

Una aplicación de escritorio desarrollada en Java, diseñada bajo el patrón de arquitectura MVC (Modelo-Vista-Controlador),que simula combates entre Pokémones, permitiendo al usuario utilizar sus Pokémones y disfrutar de una experiencia interactiva tipo videojuego.

--- 

## 📚 Tabla de Contenidos

- [🎯 Descripción](#-descripción)
- [📐 Arquitectura MVC](#-arquitectura-mvc)
- [👥 Integrantes](#-integrantes)
- [⚙️ Tecnologías Utilizadas](#️-tecnologías-utilizadas)
- [📁 Estructura del Código](#-estructura-del-código)
- [🧠 Diagrama UML](#-diagrama-uml)
- [🚀 Instalación y Ejecución](#-instalación-y-ejecución)
- [🖼️ Capturas de Pantalla](#-capturas-de-pantalla)
- [🧠 Retos y Aprendizajes](#-retos-y-aprendizajes)

---

## 🎯 Descripción

Este proyecto tiene como objetivo la creación de una GUI en Java para simular combates Pokémon con interfaz intuitiva y que implementa el patrón de arquitectura MVC (Modelo-Vista-Controlador). Los jugadores pueden utilizar habilidades especiales y experimentar un sistema de combate por turnos.

---

## 📐 Arquitectura MVC

- **Modelo**: contiene las clases que representan la lógica de negocio y los datos (Pokémon, Entrenadores, Batallas, etc.).

- **Vista**: construida con Swing/NetBeans, representa la GUI que el usuario utiliza.

- **Controlador**: maneja la interacción entre la Vista y el Modelo, coordinando eventos y actualizaciones.

---

## 👥 Integrantes

- Samuel Agudelo Sosa - Código: 202459419
- Sebastián Saenz Mejia - Código: 202459528
- Samuel Romero Martinez - Código: 202459464(Temporalmente inmovilizado el brazo derecho)
- José Manuel Castaño Rojas - Código 202459522(Acordado)

---

## ⚙️ Tecnologías Utilizadas

- ☕ Java
- 🔧 Git y Github
- 📁 Kanban (Trello o Github projects)
- 🖼️ Neatbens para la GUI
- 🧪 UML para diseño de clases y arquitectura
- 🧠 POO (Programación Orientada a Objetos)
- 🔔 Programación orientada a eventos
- 🏛️ MVC (Modelo-Vista-Controlador)

---

## 📁 Estructura del Código

```bash
Pokemon_GUI/
│
│                       
├── src/
│   ├── image/                   # Imágenes utilizadas
│   ├── controlador/             # Lógica de control
│   ├── modelo/                  # Clases de negocio y datos
│   ├── vista/                   # Interfaces gráficas (GUI)
│   ├── App.java/                # Archivo desde el cual se ejecuta todo el juego
│   
│
├── .gitignore
└── README.md
```

---

### 📊 Diagrama UML del Juego
![Diagrama UML del Juego](src/image/diagrama.png)

---

## 🚀 Instalación y ejecución
1. Clonar el repositorio:  
   ```bash
   git clone https://github.com/saenz22/Pokemon_MVC.git
---

## 🖼️ Capturas de Pantalla

Pantalla Prinicipal

![Pantalla principal](/src/image/Pantalla_principal.png)

Selección de entrenadores

![Selección de entrenadores](/src/image/Seleccion_de_entrenadores.png)

Selección de pokemones

![Selección de pokemones 1](src/image/Seleccion_de_pokemones.png)

![Selección de pokemones 2](src/image/Seleccion_de_pokemon_2.png)

Combate

![Combate](src/image/Combate.png)
![Combate 2](src/image/Combate_2.png)


---

## 🧠 Retos y Aprendizajes

- Implementación de POO y herencia entre clases Pokémon.
- Manejo de eventos en interfaces gráficas.
- Modelado de clases con UML.
- Coordinación en equipo para dividir responsabilidades.
- Diseño de interfaces amigables al usuario.
- Diseño de software con MVC.
- Manejo de eventos en GUIs.




