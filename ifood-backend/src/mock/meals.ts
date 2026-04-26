export type MockMeal = {
  mealType: string;
  placeName: string;
  placeAddress: string;
  mealName: string;
  mealDescription: string;
  mealPrice: number;
};

export const meals: MockMeal[] = [
  {
    mealType: 'breakfast',
    placeName: 'Café da Manhã Natural',
    placeAddress: 'Av. Paulista, 900 - São Paulo, SP',
    mealName: 'Açaí Bowl com Frutas',
    mealDescription: 'Bowl de açaí com banana, morango, granola e mel.',
    mealPrice: 22.90,
  },
  {
    mealType: 'breakfast',
    placeName: 'Padaria Tradicional São Paulo',
    placeAddress: 'Rua Augusta, 250 - São Paulo, SP',
    mealName: 'Pão na Chapa com Queijo',
    mealDescription: 'Pão francês grelhado com queijo mussarela derretido.',
    mealPrice: 12.50,
  },
  {
    mealType: 'lunch',
    placeName: 'Restaurante Verde Folha',
    placeAddress: 'Rua das Flores, 123 - São Paulo, SP',
    mealName: 'Bowl de Quinoa com Legumes',
    mealDescription: 'Bowl nutritivo com quinoa, legumes grelhados e molho tahine.',
    mealPrice: 32.90,
  },
  {
    mealType: 'lunch',
    placeName: 'Cantina della Nonna',
    placeAddress: 'Rua Bela Cintra, 400 - São Paulo, SP',
    mealName: 'Risotto ai Funghi',
    mealDescription: 'Risotto cremoso com mix de cogumelos e parmesão.',
    mealPrice: 45.00,
  },
  {
    mealType: 'lunch',
    placeName: 'Tokyo Ramen House',
    placeAddress: 'Liberdade, 88 - São Paulo, SP',
    mealName: 'Ramen Tonkotsu Spicy',
    mealDescription: 'Caldo de osso encorpado com macarrão, chashu e ovo marinado.',
    mealPrice: 38.50,
  },
  {
    mealType: 'lunch',
    placeName: 'Grill do Zé',
    placeAddress: 'Av. Brigadeiro Faria Lima, 1200 - São Paulo, SP',
    mealName: 'Picanha na Brasa',
    mealDescription: 'Picanha grelhada com arroz, feijão e vinagrete.',
    mealPrice: 55.00,
  },
  {
    mealType: 'dinner',
    placeName: 'Pizzaria Bella Napoli',
    placeAddress: 'Rua Consolação, 300 - São Paulo, SP',
    mealName: 'Pizza Margherita',
    mealDescription: 'Pizza com molho de tomate fresco, mussarela de búfala e manjericão.',
    mealPrice: 48.00,
  },
  {
    mealType: 'dinner',
    placeName: 'Thai Garden',
    placeAddress: 'Rua Oscar Freire, 55 - São Paulo, SP',
    mealName: 'Pad Thai Vegano',
    mealDescription: 'Macarrão de arroz salteado com tofu, broto de feijão e amendoim.',
    mealPrice: 39.90,
  },
  {
    mealType: 'dinner',
    placeName: 'Sushi Naka',
    placeAddress: 'Rua da Liberdade, 150 - São Paulo, SP',
    mealName: 'Combinado Especial 20 peças',
    mealDescription: 'Seleção de sushis e sashimis frescos do dia.',
    mealPrice: 72.00,
  },
  {
    mealType: 'afternoon_snack',
    placeName: 'Café do Parque',
    placeAddress: 'Av. Ibirapuera, 500 - São Paulo, SP',
    mealName: 'Mix de Castanhas com Frutas Secas',
    mealDescription: 'Combinação de castanhas, nozes e frutas secas selecionadas.',
    mealPrice: 18.90,
  },
  {
    mealType: 'afternoon_snack',
    placeName: 'Bistrô Levinho',
    placeAddress: 'Rua Haddock Lobo, 200 - São Paulo, SP',
    mealName: 'Crepioca com Recheio de Frango',
    mealDescription: 'Tapioca crocante recheada com frango desfiado e cream cheese.',
    mealPrice: 24.50,
  },
];
