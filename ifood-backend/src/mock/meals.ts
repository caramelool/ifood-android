export type MockMeal = {
  mealType: 'breakfast' | 'lunch' | 'dinner';
  mealTimeStart: string;
  mealTimeEnd: string;
  placeName: string;
  placeAddress: string;
  mealName: string;
  mealDescription: string;
  mealPrice: number;
  tags: string[];
};

export const meals: MockMeal[] = [
  {
    mealType: 'breakfast',
    mealTimeStart: '06:00',
    mealTimeEnd: '10:30',
    placeName: 'Café da Manhã Natural',
    placeAddress: 'Av. Paulista, 900 - São Paulo, SP',
    mealName: 'Açaí Bowl com Frutas',
    mealDescription: 'Bowl de açaí com banana, morango, granola e mel.',
    mealPrice: 22.90,
    tags: ['vegan', 'gluten-free', 'healthy'],
  },
  {
    mealType: 'breakfast',
    mealTimeStart: '07:00',
    mealTimeEnd: '11:00',
    placeName: 'Padaria Tradicional São Paulo',
    placeAddress: 'Rua Augusta, 250 - São Paulo, SP',
    mealName: 'Pão na Chapa com Queijo',
    mealDescription: 'Pão francês grelhado com queijo mussarela derretido.',
    mealPrice: 12.50,
    tags: ['classic', 'cheese'],
  },
  {
    mealType: 'lunch',
    mealTimeStart: '11:30',
    mealTimeEnd: '15:00',
    placeName: 'Restaurante Verde Folha',
    placeAddress: 'Rua das Flores, 123 - São Paulo, SP',
    mealName: 'Bowl de Quinoa com Legumes',
    mealDescription: 'Bowl nutritivo com quinoa, legumes grelhados e molho tahine.',
    mealPrice: 32.90,
    tags: ['vegan', 'gluten-free', 'healthy'],
  },
  {
    mealType: 'lunch',
    mealTimeStart: '12:00',
    mealTimeEnd: '15:00',
    placeName: 'Cantina della Nonna',
    placeAddress: 'Rua Bela Cintra, 400 - São Paulo, SP',
    mealName: 'Risotto ai Funghi',
    mealDescription: 'Risotto cremoso com mix de cogumelos e parmesão.',
    mealPrice: 45.00,
    tags: ['italian', 'vegetarian'],
  },
  {
    mealType: 'lunch',
    mealTimeStart: '11:00',
    mealTimeEnd: '14:30',
    placeName: 'Tokyo Ramen House',
    placeAddress: 'Liberdade, 88 - São Paulo, SP',
    mealName: 'Ramen Tonkotsu Spicy',
    mealDescription: 'Caldo de osso encorpado com macarrão, chashu e ovo marinado.',
    mealPrice: 38.50,
    tags: ['japanese', 'spicy'],
  },
  {
    mealType: 'lunch',
    mealTimeStart: '11:30',
    mealTimeEnd: '14:00',
    placeName: 'Grill do Zé',
    placeAddress: 'Av. Brigadeiro Faria Lima, 1200 - São Paulo, SP',
    mealName: 'Picanha na Brasa',
    mealDescription: 'Picanha grelhada com arroz, feijão e vinagrete.',
    mealPrice: 55.00,
    tags: ['barbecue', 'meat'],
  },
  {
    mealType: 'dinner',
    mealTimeStart: '18:00',
    mealTimeEnd: '23:00',
    placeName: 'Pizzaria Bella Napoli',
    placeAddress: 'Rua Consolação, 300 - São Paulo, SP',
    mealName: 'Pizza Margherita',
    mealDescription: 'Pizza com molho de tomate fresco, mussarela de búfala e manjericão.',
    mealPrice: 48.00,
    tags: ['italian', 'vegetarian', 'pizza'],
  },
  {
    mealType: 'dinner',
    mealTimeStart: '19:00',
    mealTimeEnd: '23:30',
    placeName: 'Thai Garden',
    placeAddress: 'Rua Oscar Freire, 55 - São Paulo, SP',
    mealName: 'Pad Thai Vegano',
    mealDescription: 'Macarrão de arroz salteado com tofu, broto de feijão e amendoim.',
    mealPrice: 39.90,
    tags: ['thai', 'vegan', 'gluten-free', 'spicy'],
  },
  {
    mealType: 'dinner',
    mealTimeStart: '18:30',
    mealTimeEnd: '22:00',
    placeName: 'Sushi Naka',
    placeAddress: 'Rua da Liberdade, 150 - São Paulo, SP',
    mealName: 'Combinado Especial 20 peças',
    mealDescription: 'Seleção de sushis e sashimis frescos do dia.',
    mealPrice: 72.00,
    tags: ['japanese', 'seafood', 'gluten-free'],
  },
];
