export type MockMeal = {
  mealType: string;
  placeName: string;
  placeAddress: string;
  mealName: string;
  mealDescription: string;
  mealPrice: number;
  mealImageUrl: string;
};

export const meals: MockMeal[] = [
  {
    mealType: 'breakfast',
    placeName: 'Café da Manhã Natural',
    placeAddress: 'Av. Paulista, 900 - São Paulo, SP',
    mealName: 'Açaí Bowl com Frutas',
    mealDescription: 'Bowl de açaí com banana, morango, granola e mel.',
    mealPrice: 22.90,
    mealImageUrl: 'https://levementenacozinha.com.br/wp-content/uploads/2022/08/IMG_3049-scaled-e1661886314928.jpg',
  },
  {
    mealType: 'breakfast',
    placeName: 'Padaria Tradicional São Paulo',
    placeAddress: 'Rua Augusta, 250 - São Paulo, SP',
    mealName: 'Pão na Chapa com Queijo',
    mealDescription: 'Pão francês grelhado com queijo mussarela derretido.',
    mealPrice: 12.50,
    mealImageUrl: 'https://www.sabornamesa.com.br/media/k2/items/cache/66121bc28c9d23f8a6cd26cc53117b58_XL.jpg',
  },
  {
    mealType: 'lunch',
    placeName: 'Restaurante Verde Folha',
    placeAddress: 'Rua das Flores, 123 - São Paulo, SP',
    mealName: 'Bowl de Quinoa com Legumes',
    mealDescription: 'Bowl nutritivo com quinoa, legumes grelhados e molho tahine.',
    mealPrice: 32.90,
    mealImageUrl: 'https://www.comidaereceitas.com.br/wp-content/uploads/2025/08/bowl-quinoa-frango.jpg',
  },
  {
    mealType: 'lunch',
    placeName: 'Cantina della Nonna',
    placeAddress: 'Rua Bela Cintra, 400 - São Paulo, SP',
    mealName: 'Risotto ai Funghi',
    mealDescription: 'Risotto cremoso com mix de cogumelos e parmesão.',
    mealPrice: 45.00,
    mealImageUrl: 'https://farchioni1780.com/cdn/shop/articles/risotto-ai-funghi-porcini-ricette-farchioni_5e0c3800-ea31-4b5c-88f9-95cbfbd75200.jpg?v=1748597124',
  },
  {
    mealType: 'lunch',
    placeName: 'Tokyo Ramen House',
    placeAddress: 'Liberdade, 88 - São Paulo, SP',
    mealName: 'Ramen Tonkotsu Spicy',
    mealDescription: 'Caldo de osso encorpado com macarrão, chashu e ovo marinado.',
    mealPrice: 38.50,
    mealImageUrl: 'https://i0.wp.com/cringeykitchen.com/wp-content/uploads/2019/09/LRM_EXPORT_1639059543988679_20190612_181240755.jpg?w=1669&ssl=1',
  },
  {
    mealType: 'lunch',
    placeName: 'Grill do Zé',
    placeAddress: 'Av. Brigadeiro Faria Lima, 1200 - São Paulo, SP',
    mealName: 'Picanha na Brasa',
    mealDescription: 'Picanha grelhada com arroz, feijão e vinagrete.',
    mealPrice: 55.00,
    mealImageUrl: 'https://media-cdn.tripadvisor.com/media/photo-s/13/09/98/e5/picanha-na-brasa.jpg',
  },
  {
    mealType: 'dinner',
    placeName: 'Pizzaria Bella Napoli',
    placeAddress: 'Rua Consolação, 300 - São Paulo, SP',
    mealName: 'Pizza Margherita',
    mealDescription: 'Pizza com molho de tomate fresco, mussarela de búfala e manjericão.',
    mealPrice: 48.00,
    mealImageUrl: 'https://www.receitasnestle.com.br/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/494feec171f5683665eba434d22e52f5.webp?itok=n3xpYgtR',
  },
  {
    mealType: 'dinner',
    placeName: 'Thai Garden',
    placeAddress: 'Rua Oscar Freire, 55 - São Paulo, SP',
    mealName: 'Pad Thai Vegano',
    mealDescription: 'Macarrão de arroz salteado com tofu, broto de feijão e amendoim.',
    mealPrice: 39.90,
    mealImageUrl: 'https://d36fw6y2wq3bat.cloudfront.net/recipes/pad-thai-vegano/900/pad-thai-vegano_version_1670644977.jpg',
  },
  {
    mealType: 'dinner',
    placeName: 'Sushi Naka',
    placeAddress: 'Rua da Liberdade, 150 - São Paulo, SP',
    mealName: 'Combinado Especial 20 peças',
    mealDescription: 'Seleção de sushis e sashimis frescos do dia.',
    mealPrice: 72.00,
    mealImageUrl: 'https://speedy.uenicdn.com/caf63b76-14db-42a8-a03a-cb170e608833/c1024_a/image/upload/v1520866082/service_images/shutterstock_580685365.jpg',
  },
  {
    mealType: 'afternoon_snack',
    placeName: 'Café do Parque',
    placeAddress: 'Av. Ibirapuera, 500 - São Paulo, SP',
    mealName: 'Mix de Castanhas com Frutas Secas',
    mealDescription: 'Combinação de castanhas, nozes e frutas secas selecionadas.',
    mealPrice: 18.90,
    mealImageUrl: 'https://sommeliergrandcru.wordpress.com/wp-content/uploads/2016/12/nuts-mix-castanhas-oleogionosas-natal-ceia-frutas-secas.jpg?w=1248',
  },
  {
    mealType: 'afternoon_snack',
    placeName: 'Bistrô Levinho',
    placeAddress: 'Rua Haddock Lobo, 200 - São Paulo, SP',
    mealName: 'Crepioca com Recheio de Frango',
    mealDescription: 'Tapioca crocante recheada com frango desfiado e cream cheese.',
    mealPrice: 24.50,
    mealImageUrl: 'https://p2.trrsf.com/image/fget/cf/942/530/images.terra.com/2022/10/11/1778242455-crepioca-recheada-com-frango.jpg',
  },
];
