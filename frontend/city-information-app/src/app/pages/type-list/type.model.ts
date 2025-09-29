export interface TypeI {
  id: number;
  type: string;
  path_file: string;
  description: string;
}

export interface MessageResponse {
  message: TypeI[];
}