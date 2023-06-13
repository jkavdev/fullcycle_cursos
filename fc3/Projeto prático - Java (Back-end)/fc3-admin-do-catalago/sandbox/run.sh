# Criar as docker networks
docker network create adm_videos_services

# Criar as pastas com permissões
mkdir -m 777 .docker
mkdir -m 777 .docker/keycloak
mkdir -m 777 .docker/mysql

docker compose -f services/docker-compose.yml up -d

echo "Inicializando os containers..."
sleep 10